package com.example.solartracker

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.solartracker.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        val homeFragment = HomeFragment()
        val positionFragment = PositionFragment()
        val batteryFragment = BatteryFragment()
        val settingsFragment = SettingsFragment()

        supportActionBar?.title = "Dashboard"
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#2D2D2D")))

        makeCurrentFragment(homeFragment)

        binding.navBottom.setOnNavigationItemSelectedListener() {
            when(it.itemId){
                R.id.nav_home -> {
                    makeCurrentFragment(homeFragment)
                    supportActionBar?.title = "Dashboard"
                }
                R.id.nav_position -> {
                    makeCurrentFragment(positionFragment)
                    supportActionBar?.title = "Position"
                }
                R.id.nav_battery -> {
                    makeCurrentFragment(batteryFragment)
                    supportActionBar?.title = "Battery"
                }
                R.id.nav_setting -> {
                    makeCurrentFragment(settingsFragment)
                    supportActionBar?.title = "Settings"
                }
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.item_menu_actionbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.act_profile -> {
                startActivity(Intent(this, ProfileActivity::class.java))
            }
            R.id.act_about -> {
                AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
                    .setTitle("Versi Aplikasi")
                    .setMessage("Beta 1.0.0")
                    .setCancelable(true)
                    .setPositiveButton("OK", null)
                    .create()
                    .show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fragment).commit()
        }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser == null){
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

}