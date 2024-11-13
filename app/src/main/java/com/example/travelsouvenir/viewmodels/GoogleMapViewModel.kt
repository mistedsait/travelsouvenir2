package com.example.travelsouvenir.viewmodels

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import javax.inject.Inject

class LandmarkMapViewModel @Inject constructor() : ViewModel() {

    // MutableLiveData to store route points
    private val _routePoints = MutableLiveData<List<LatLng>>()
    val routePoints: LiveData<List<LatLng>> get() = _routePoints

    // Function to fetch route using the Directions API
    fun fetchRoute(origin: Location, destination: String, apiKey: String) {
        val originLatLng = "${origin.latitude},${origin.longitude}"
        viewModelScope.launch {
            _routePoints.value = getRouteData(originLatLng, destination, apiKey)
        }
    }


    // Helper method to fetch data from Google Directions API
    private suspend fun getRouteData(origin: String, destination: String, apiKey: String): List<LatLng> {
        val url = "https://maps.googleapis.com/maps/api/directions/json?origin=$origin&destination=$destination&key=$apiKey"
        return withContext(Dispatchers.IO) {
            try {
                val result = URL(url).readText()
                val jsonObject = JSONObject(result)
                if (jsonObject.getString("status") == "OK") {
                    val route = jsonObject.getJSONArray("routes").getJSONObject(0)
                    val polyline = route.getJSONObject("overview_polyline").getString("points")
                    decodePolyline(polyline)
                } else {
                    Log.e("API_ERROR", "Error fetching directions: ${jsonObject.getString("status")}")
                    emptyList()  // Return empty list in case of error
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Exception fetching directions: ${e.message}")
                emptyList()  // Return empty list in case of error
            }
        }
    }


    // Polyline decoder for Google Maps
    private fun decodePolyline(encoded: String): List<LatLng> {
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            poly.add(LatLng(lat / 1E5, lng / 1E5))
        }
        return poly
    }
}
