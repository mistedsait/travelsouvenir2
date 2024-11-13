package com.example.travelsouvenir.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LaunchViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LaunchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LaunchViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
