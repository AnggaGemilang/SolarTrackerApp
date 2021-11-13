package com.example.solartracker

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.solartracker.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "My Profile"
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#2D2D2D")))

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnLogout.setOnClickListener {

            AlertDialog.Builder(this)
                    .setTitle("Perhatian")
                    .setMessage("Apakah Anda Yakin?")
                    .setCancelable(false)
                    .setPositiveButton("Yes"){ _, _ ->
                        firebaseAuth.signOut()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                    .setNegativeButton("No", null)
                    .create()
                    .show()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}