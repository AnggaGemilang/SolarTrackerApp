package com.example.solartracker.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
        val nama: String,
        val username: String,
        val email: String,
        val password: String
) : Parcelable