package com.example.solartracker

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.solartracker.utils.MyAlarmReceiver

class MyPreferenceFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var LOVE: String
    private lateinit var isLoveMuPreference: CheckBoxPreference
    private lateinit var alarmReceiver: MyAlarmReceiver

    companion object {
        private const val DEFAULT_VALUE = "Tidak Ada"
    }

    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(R.xml.preferences)
        init()
        setSummaries()
    }

    private fun init() {
        alarmReceiver = MyAlarmReceiver()
        LOVE = "love"
        isLoveMuPreference = (findPreference<CheckBoxPreference>(LOVE) as CheckBoxPreference)
    }

    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        isLoveMuPreference.isChecked = sh.getBoolean(LOVE, false)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences, p1: String) {
        if (p1 == LOVE) {
            isLoveMuPreference.isChecked = p0.getBoolean(LOVE, false)
        }

        val status = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(LOVE, false)

        if(status) {
            alarmReceiver.setRepeatingAlarm(requireContext(), "18:01","MUNCUL_NOTIP_DONG")
        } else {
            alarmReceiver.cancelAlarm(requireContext())
        }
    }

}