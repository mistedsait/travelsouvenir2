package com.example.travelsouvenir.utils

import android.content.Context
import android.content.SharedPreferences

class LaunchChecker(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

    fun isFirstLaunch(): Boolean {
        val isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true)
        if (isFirstLaunch) {
            // Mark as not the first launch anymore
            sharedPreferences.edit().putBoolean("isFirstLaunch", false).apply()
        }
        return isFirstLaunch
    }
}
