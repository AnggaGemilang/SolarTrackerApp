package com.example.solartracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.solartracker.adapter.OnboardingAdapter
import com.example.solartracker.data.OnboardingData
import com.google.android.material.tabs.TabLayout

class OnboardingActivity : AppCompatActivity() {

    var onBoardingAdapter: OnboardingAdapter? = null
    var tabLayout: TabLayout? = null
    var onBoardingViewPager : ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        tabLayout = findViewById(R.id.tabLayout)

        val onboardingData: MutableList<OnboardingData> = ArrayList()
        onboardingData.add(OnboardingData("asdasd", "asdasd", R.drawable.onboarding_asset1))
        onboardingData.add(OnboardingData("dfgdgdfgdfg", "dgdgdfgdfgdfg", R.drawable.onboarding_asset1))

        setOnBoardingViewPagerAdapter(onboardingData)

    }

    private fun setOnBoardingViewPagerAdapter(onboardingDataList: List<OnboardingData>){
        onBoardingViewPager = findViewById(R.id.slider)

        onBoardingAdapter = OnboardingAdapter(this, onboardingDataList)

        onBoardingViewPager!!.adapter = onBoardingAdapter

        tabLayout?.setupWithViewPager(onBoardingViewPager)

    }
}