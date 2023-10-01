package com.example.cookbook.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.cookbook.R

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)
        val btnCookBook = findViewById<ImageButton>(R.id.cookbook_button)
        val btnNote = findViewById<ImageButton>(R.id.note_button)

        btnCookBook.setOnClickListener {
            val intent = Intent(this, CookBookActivity::class.java)
            startActivity(intent)
        }

        btnNote.setOnClickListener {
            val intent = Intent(this, CheckUserActivity::class.java)
            startActivity(intent)
        }
    }
}