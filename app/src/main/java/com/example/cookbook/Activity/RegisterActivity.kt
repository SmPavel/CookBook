package com.example.cookbook.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.cookbook.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {
    private lateinit var btnSignUp: Button
    private lateinit var btnBack: Button

    private lateinit var etEmail: EditText
    private lateinit var etPass: EditText
    private lateinit var etConfPass: EditText

    private val TAG = "RegisterActivity"

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_page)

        btnSignUp = findViewById(R.id.signUp)
        btnBack = findViewById(R.id.back)

        etEmail = findViewById(R.id.email)
        etPass = findViewById(R.id.password)
        etConfPass = findViewById(R.id.password_confirm)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        btnBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        btnSignUp.setOnClickListener {
            signUp()
        }
    }

    private fun signUp() {
        val email = etEmail.text.toString()
        val pass = etPass.text.toString()
        val confirmPassword = etConfPass.text.toString()

        if (email.isBlank() || pass.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(this, "Username and Password can't be blank", Toast.LENGTH_SHORT)
                .show()
            return
        }

        if (pass != confirmPassword) {
            Toast.makeText(
                this,
                "Password and Confirm Password do not match",
                Toast.LENGTH_SHORT
            )
                .show()
            return
        }
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        val userRef = FirebaseFirestore.getInstance().collection("userProfiles").document(user.uid)

                        val userData = hashMapOf(
                            "email" to email
                        )

                        userRef.set(userData)
                            .addOnSuccessListener {
                                Log.d(TAG, "User profile created successfully")
                                val intent = Intent(this, NoteActivity::class.java)
                                startActivity(intent)
                            }
                            .addOnFailureListener { error ->
                                Log.w(TAG, "Error creating user profile:", error)
                                Toast.makeText(
                                    this, "Authentication failed.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        this, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}