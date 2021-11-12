package com.example.solartracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val signin: TextView = findViewById(R.id.signin)

        signin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }
}