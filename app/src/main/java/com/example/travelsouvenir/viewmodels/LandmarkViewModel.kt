package com.example.travelsouvenir.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelsouvenir.data.LandmarkEntity
import com.example.travelsouvenir.data.LandmarkRepository
import com.example.travelsouvenir.pages.Place
import com.example.travelsouvenir.pages.landmarks.Landmark
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandmarkViewModel @Inject constructor(
    private val landmarkRepository: LandmarkRepository

) : ViewModel() {
    private val _landmarks = MutableLiveData<List<Landmark>>()
    val landmarks: LiveData<List<Landmark>> get() = _landmarks

    fun saveLandmark(landmark: LandmarkEntity) {
        viewModelScope.launch {
            landmarkRepository.insertLandmark(landmark)
        }
    }

    val allLandmarks: List<Landmark>
        get() = _landmarks.value ?: emptyList()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()


    val filteredPlaces = searchQuery
        .debounce(400)
        .mapLatest { query ->
            if (query.isEmpty()) {
                _landmarks.value ?: emptyList()
            } else {
                _landmarks.value?.filter { place ->
                    place.name.contains(query, ignoreCase = true)
                } ?: emptyList()
            }
        }


    fun loadLandmarks(titles: List<String>) {
        viewModelScope.launch {
            val currentLandmarks = mutableListOf<Landmark>()
            for (title in titles) {
                try {
                    val landmarkEntity = landmarkRepository.fetchLandmarkInfo(title)
                    landmarkRepository.insertLandmark(landmarkEntity)

                    val landmark = Landmark(
                        id = landmarkEntity.id.toInt(),
                        name = landmarkEntity.name,
                        description = landmarkEntity.description,
                        originalImage = landmarkEntity.originalImage ?: "",
                    )
                    currentLandmarks.add(landmark)

                    Log.d("API_SUCCESS", "Successfully fetched data for $title")
                } catch (e: Exception) {
                    Log.e("API_ERROR", "Error fetching data for $title", e)
                }
            }
            _landmarks.value = currentLandmarks
        }
    }
    fun loadLandmarksFromDatabase() {
        viewModelScope.launch {
            val landmarkEntities = landmarkRepository.getAllLandmarks()
            val landmarks = landmarkEntities.map { entity ->
                Landmark(
                    id = entity.id.toInt(),
                    name = entity.name,
                    description = entity.description,
                    originalImage = entity.originalImage ?: ""
                )
            }
            _landmarks.value = landmarks
        }
    }

    suspend fun getAllLandmarks(): List<LandmarkEntity> {
        return landmarkRepository.getAllLandmarks()
    }

    suspend fun getLandmarkById(id: Long): LandmarkEntity {
        return landmarkRepository.getLandmarkById(id)
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}
