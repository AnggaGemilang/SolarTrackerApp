package com.example.solartracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val signup: TextView = findViewById(R.id.signup)

        signup.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }
}