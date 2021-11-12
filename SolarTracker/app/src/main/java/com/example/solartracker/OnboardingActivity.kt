package com.example.solartracker

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.solartracker.adapter.OnboardingAdapter
import com.example.solartracker.data.OnboardingData
import com.google.android.material.tabs.TabLayout

class OnboardingActivity : AppCompatActivity() {

    var onBoardingAdapter: OnboardingAdapter? = null
    var tabLayout: TabLayout? = null
    var onBoardingViewPager : ViewPager? = null
    var next: Button? = null
    var prev: Button? = null
    var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        tabLayout = findViewById(R.id.tabLayout)
        next = findViewById(R.id.next)
        prev = findViewById(R.id.prev)

        val onboardingData: MutableList<OnboardingData> = ArrayList()
        onboardingData.add(OnboardingData("User Friendly", "Saking mudahnya aplikasi ini dapat digunakan oleh bayi", R.drawable.onboarding_asset1))
        onboardingData.add(OnboardingData("Monitoring Easily", "Dengan produk ini, kita dapat memantau kinerja dari solar tracker yang kita miliki", R.drawable.onboarding_asset1))
        onboardingData.add(OnboardingData("High Quality", "Produk ini sudah berkualitas SNI (Standar Negara Indonesia)", R.drawable.onboarding_asset1))
        setOnBoardingViewPagerAdapter(onboardingData)

        next?.setOnClickListener {
            if(position < onboardingData.size-1){
                position++
                onBoardingViewPager!!.currentItem = position
            } else {
                Log.d("Testing", "asdasd")
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }

        prev?.setOnClickListener {
            if(position > 0){
                position--
            } else {
                position = onboardingData.size-1
            }
            onBoardingViewPager!!.currentItem = position
        }

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                position = tab!!.position
                if(tab.position == onboardingData.size - 1){
                    next!!.text = "Get Started"
                    next!!.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.blue));
                } else {
                    next!!.text = "Next"
                    next!!.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.black_750));
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

    }

    private fun setOnBoardingViewPagerAdapter(onboardingDataList: List<OnboardingData>){
        onBoardingViewPager = findViewById(R.id.slider)

        onBoardingAdapter = OnboardingAdapter(this, onboardingDataList)

        onBoardingViewPager!!.adapter = onBoardingAdapter

        tabLayout?.setupWithViewPager(onBoardingViewPager)

    }
}