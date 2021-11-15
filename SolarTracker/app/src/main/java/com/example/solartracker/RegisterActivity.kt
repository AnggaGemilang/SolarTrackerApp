package com.example.solartracker

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.solartracker.data.User
import com.example.solartracker.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseRef : DatabaseReference

    private var email: String = ""
    private var password: String = ""
    private var username: String = ""
    private var nama: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this, R.style.MyAlertDialogStyle)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Creating Account...")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.signin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseRef = FirebaseDatabase.getInstance().getReference("USERS")
        binding.btnSignup.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        email = binding.emailEdt2.text.toString().trim()
        password = binding.passwordEdt2.text.toString()
        nama = binding.namaEdt.text.toString()
        username = binding.usernameEdt.text.toString()

        if (TextUtils.isEmpty(nama)){
            binding.namaEdt.error = "Please Enter Name"
        } else if (TextUtils.isEmpty(username)){
            binding.usernameEdt.error = "Please Enter Username"
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailEdt2.error = "Invalid Email Format"
        } else if (TextUtils.isEmpty(password)){
            binding.passwordEdt2.error = "Please Enter Password"
        } else {
            firebaseRegister()
        }
    }

    private fun firebaseRegister() {
        progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    val firebaseUser = firebaseAuth.currentUser
                    firebaseRef.child(firebaseUser!!.uid).setValue(User(nama, username, email, password))
                            .addOnCompleteListener {
                                Toast.makeText(this, "Account created, $email", Toast.LENGTH_LONG).show()
                                startActivity(Intent(this, MainActivity::class.java))
                            }
                }
                .addOnFailureListener { e ->
                    progressDialog.dismiss()
                    Toast.makeText(this, "Sign Up Failed due to ${e.message}", Toast.LENGTH_LONG).show()
                }

    }
}