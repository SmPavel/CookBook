package com.example.cookbook.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.cookbook.R

class UnauthorizedUserMessageActivity : AppCompatActivity(){
    private lateinit var btnRegister : Button
    private lateinit var btnLogin : Button
    private lateinit var btnBack : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.unauthorized_user_message_page)
        btnRegister = findViewById(R.id.signUp_button)
        btnLogin = findViewById(R.id.signIn_button)
        btnBack = findViewById(R.id.back_button)

        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btnBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}