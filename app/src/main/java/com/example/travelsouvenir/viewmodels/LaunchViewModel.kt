package com.example.travelsouvenir.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.travelsouvenir.utils.LaunchChecker

class LaunchViewModel(context: Context) : ViewModel() {
    private val launchChecker = LaunchChecker(context)
    val isFirstLaunch: Boolean = launchChecker.isFirstLaunch()
}