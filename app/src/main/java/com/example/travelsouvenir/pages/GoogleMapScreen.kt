package com.example.travelsouvenir.pages

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.travelsouvenir.viewmodels.LandmarkMapViewModel
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*


@Composable
fun LandmarkMapScreen(
    landmarkName: String,
    landmarkLocation: LatLng,
    apiKey: String,
    viewModel: LandmarkMapViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val routePoints by viewModel.routePoints.observeAsState(emptyList())

    // State to hold the user's current location
    val locationState = remember { mutableStateOf<Location?>(null) }

    // Use LocationServices to get the fusedLocationClient
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    // LocationRequest for the updates
    val locationRequest = LocationRequest.create().apply {
        interval = 10000  // Update every 10 seconds
        fastestInterval = 5000  // Fastest update every 5 seconds
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    // Start location updates when the composable is first displayed
    LaunchedEffect(Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        locationState.value = locationResult.lastLocation
                        Log.d("Location", "Location updated: ${locationResult.lastLocation?.latitude}, ${locationResult.lastLocation?.longitude}")
                    }
                },
                Looper.getMainLooper()
            )
        }
    }

    // Fetch the route when the location is available
    LaunchedEffect(locationState.value) {
        val origin = locationState.value
        val destination = landmarkName  // Landmark name (e.g., "Eiffel Tower")

        if (origin != null) {
            viewModel.fetchRoute(origin, destination, apiKey)
        } else {
            Log.e("Location", "User's location is not available")
        }
    }

    // Show a message while fetching the route
    if (routePoints.isEmpty()) {
        Text(text = "Fetching route...", textAlign = TextAlign.Center)
    } else {
        // Show the map with route
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(landmarkLocation, 10f) // Default position if user location is unavailable
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = MapProperties(isMyLocationEnabled = true),  // Show user's location
            uiSettings = MapUiSettings(zoomControlsEnabled = true),
            cameraPositionState = cameraPositionState
        ) {
            // If the user's location is available, update camera position to zoom in
            locationState.value?.let { location ->
                val cameraPosition = CameraPosition.fromLatLngZoom(
                    LatLng(location.latitude, location.longitude),
                    15f // Zoom level
                )
                cameraPositionState.position = cameraPosition // Update the camera position
            }

            // Create MarkerState with landmark position
            val markerState = remember { MarkerState(position = landmarkLocation) }

            // Add marker at landmark location
            Marker(
                state = markerState,
                title = landmarkName
            )

            // Draw polyline for the route
            Polyline(points = routePoints)
        }
    }
}



