package com.example.cookbook.Activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cookbook.R
import com.google.firebase.auth.FirebaseAuth

class AboutUserActivity : AppCompatActivity() {
    private lateinit var btnBack: Button
    private lateinit var btnLogout: Button

    private lateinit var vtLogin: TextView

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContentView(R.layout.about_user_page)

        auth = FirebaseAuth.getInstance()

        btnLogout = findViewById(R.id.logout)

        btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        btnBack = findViewById(R.id.back)

        btnBack.setOnClickListener {
            val intent = Intent(this, NoteActivity::class.java)
            startActivity(intent)
        }
        vtLogin = findViewById(R.id.login_text)

        vtLogin.text = FirebaseAuth.getInstance().currentUser!!.email

    }
}