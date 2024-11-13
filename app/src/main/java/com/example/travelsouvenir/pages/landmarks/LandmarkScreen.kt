package com.example.travelsouvenir.pages.landmarks

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.travelsouvenir.pages.NoPlace
import com.example.travelsouvenir.viewmodels.LandmarkViewModel

@Composable
fun LandmarkScreen(
    navController: NavHostController,
    viewModel: LandmarkViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.loadLandmarksFromDatabase()
    }

    val landmarks by viewModel.landmarks.observeAsState(emptyList())

    if (landmarks.isEmpty()) {
        NoPlace()
    } else {
        DisplayAllLandmarks(landmarks) { landmark ->
            // Navigate to GoogleMapsScreen with landmark's name
            val validRoute = "GoogleMapsScreen/${landmark.name}"
            navController.navigate(validRoute)

        }
    }
}
