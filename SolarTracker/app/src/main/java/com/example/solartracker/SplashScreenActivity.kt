package com.example.solartracker

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.solartracker.databinding.ActivitySplashScreenBinding
import com.google.firebase.auth.FirebaseAuth

@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        var progressStatus = 0
        binding.progressBar.max = 100

        Thread {
            while (progressStatus < binding.progressBar.max) {
                progressStatus++
                Log.d("Progress bar", progressStatus.toString())
                binding.progressBar.progress = progressStatus
                try {
                    Thread.sleep(50)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            Handler(Looper.getMainLooper()).post {
                if(firebaseAuth.currentUser != null){
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    startActivity(Intent(this, OnboardingActivity::class.java))
                }
            }
        }.start()
    }
}