package com.example.solartracker

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val pg: ProgressBar = findViewById(R.id.progress_bar)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        var progressStatus = 0
        pg.max = 100

        Thread {
            while (progressStatus < pg.max) {
                progressStatus++
                Log.d("Progress bar", progressStatus.toString())
                pg.progress = progressStatus
                try {
                    Thread.sleep(50)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            Handler(Looper.getMainLooper()).post {
                startActivity(Intent(this, OnboardingActivity::class.java))
            }
        }.start()
    }
}