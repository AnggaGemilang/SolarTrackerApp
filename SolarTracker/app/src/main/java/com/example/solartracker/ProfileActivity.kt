package com.example.solartracker

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.solartracker.data.User
import com.example.solartracker.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var firebaseRef: DatabaseReference

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "My Profile"
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#2D2D2D")))

        firebaseAuth = FirebaseAuth.getInstance()

        firebaseRef = FirebaseDatabase.getInstance().getReference("USERS").child(firebaseAuth.currentUser!!.uid)

        firebaseRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(p0: DataSnapshot) {
                binding.edtEmail.setText(p0.child("email").value.toString())
                binding.edtNama.setText(p0.child("nama").value.toString())
                binding.txtNama.setText(p0.child("nama").value.toString())
                binding.txtUsername.setText(p0.child("username").value.toString())
                binding.edtUsername.setText(p0.child("username").value.toString())
                binding.edtPassword.setText(p0.child("password").value.toString())
            }
        })

        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(this)
                    .setTitle("Perhatian")
                    .setMessage("Apakah Anda Yakin?")
                    .setCancelable(false)
                    .setPositiveButton("Yes"){ _, _ ->
                        firebaseAuth.signOut()
                        Toast.makeText(this, "Logout Successfully", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                    .setNegativeButton("No", null)
                    .create()
                    .show()
        }

        binding.btnSave.setOnClickListener {
            val dataUser = User(binding.edtNama.text.toString(), binding.edtUsername.text.toString(), binding.edtEmail.text.toString(), binding.edtPassword.text.toString())
            firebaseRef = FirebaseDatabase.getInstance().getReference("USERS").child(firebaseAuth.currentUser!!.uid)
            firebaseRef.setValue(dataUser)
            Toast.makeText(this, "Updated Successfully", Toast.LENGTH_LONG).show()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}