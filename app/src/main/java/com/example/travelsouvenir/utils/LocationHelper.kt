package com.example.travelsouvenir.utils

import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import com.example.travelsouvenir.viewmodels.PlacesViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationCallback
import java.util.Locale
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import android.os.Looper
import com.google.maps.model.LatLng

//CHARLES PROXY, NGROK, augmented reality
class LocationHelper(private val context: Context, private val viewModel: PlacesViewModel) {

    fun startLocationUpdates(
        fusedLocationClient: FusedLocationProviderClient,
        locationRequest: LocationRequest,
        locationState: MutableState<Location?>
    ) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

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
    fun getLatLngFromLandmarkName(landmarkName: String, context: Context): LatLng? {
        val geocoder = Geocoder(context, Locale.getDefault())
        return try {
            // Get the list of addresses matching the landmark name
            val addresses = geocoder.getFromLocationName(landmarkName, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                // Get the first result and extract its latitude and longitude
                val address = addresses[0]
                LatLng(address.latitude, address.longitude)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("Geocoding", "Error getting LatLng for $landmarkName", e)
            null
        }
    }
    fun getCityNameFromLocation(
        locationClient: FusedLocationProviderClient,
        imageUri: Uri
    ) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("Location", "Location permission not granted.")
            return
        }

        locationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val geocoder = Geocoder(context, Locale.ENGLISH)
                val addressList: List<android.location.Address>?

                try {
                    addressList = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    if (!addressList.isNullOrEmpty()) {
                        var cityName = addressList[0].locality ?: "Unknown"
                        cityName = when (cityName) {
                            "Wien" -> "Vienna"
                            "München" -> "Munich"
                            "Köln" -> "Cologne"
                            "Roma" -> "Rome"
                            "Milano" -> "Milan"
                            "Firenze" -> "Florence"
                            "Torino" -> "Turin"
                            "Genève" -> "Geneva"
                            "Bruxelles" -> "Brussels"
                            "Praha" -> "Prague"
                            "Warszawa" -> "Warsaw"
                            "Kraków" -> "Krakow"
                            "Athína" -> "Athens"
                            "Lisboa" -> "Lisbon"
                            "Sevilla" -> "Seville"
                            "Zürich" -> "Zurich"
                            "Antwerpen" -> "Antwerp"
                            "Venezia" -> "Venice"
                            "Napoli" -> "Naples"
                            "Moskva" -> "Moscow"
                            "Beograd" -> "Belgrade"
                            "Sankt-Peterburg" -> "Saint Petersburg"
                            "Братислава" -> "Bratislava"
                            else -> cityName
                        }
                        Log.d("Location", "City Name: $cityName")
                        viewModel.loadPlaces(listOf(cityName))
                        PhotoHelper(context).createAlbumAndSavePhoto(cityName, imageUri)
                    } else {
                        Log.d("Location", "No address found")
                    }
                } catch (e: Exception) {
                    Log.e("Location", "Error retrieving address", e)
                }
            } else {
                Log.d("Location", "Location is null")
            }
        }
    }
}
