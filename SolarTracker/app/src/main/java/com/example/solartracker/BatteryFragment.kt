package com.example.solartracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mikhaellopez.circularprogressbar.CircularProgressBar


class BatteryFragment : Fragment() {

    private var _binding: BatteryFragment? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_battery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val progressBar = view.findViewById<CircularProgressBar>(R.id.spinner)
        val tvSpinner = view.findViewById<TextView>(R.id.tv_spinner)

        progressBar.apply {
            setProgressWithAnimation(40f, 3000)
            tvSpinner.text = "40%"
        }

    }
}