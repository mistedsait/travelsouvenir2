package com.example.travelsouvenir.viewmodels

import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelsouvenir.data.PlaceRepository
import com.example.travelsouvenir.pages.Place
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val placeRepository: PlaceRepository
) : ViewModel() {
    private val _places = MutableLiveData<List<Place>>()
    val places: LiveData<List<Place>> get() = _places

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()


    val allPlaces: List<Place>
        get() = _places.value ?: emptyList()

    val filteredPlaces = searchQuery
        .debounce(400)
        .mapLatest { query ->
            val allPlaces = _places.value ?: emptyList()
            if (query.isEmpty()) {
                allPlaces
            } else {
                allPlaces.filter { place ->
                    place.name.contains(query, ignoreCase = true)
                }
            }
        }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun loadPlaces(titles: List<String>) {
        viewModelScope.launch {
            val currentPlaces = mutableListOf<Place>()
            for (title in titles) {
                try {
                    val placeEntity = placeRepository.fetchPlaceInfo(title)
                    placeRepository.insertPlace(placeEntity)

                    val place = Place(
                        id = placeEntity.id.toInt(),
                        name = placeEntity.name,
                        description = placeEntity.description,
                        originalImage = placeEntity.originalImage ?: "",
                        detailedDescription = placeEntity.detailedDescription
                    )
                    currentPlaces.add(place)

                    Log.d("API_SUCCESS", "Successfully fetched data for $title")
                } catch (e: Exception) {
                    Log.e("API_ERROR", "Error fetching data for $title", e)
                }
            }
            _places.value = currentPlaces
        }
    }

    fun loadPlacesFromDatabase() {
        viewModelScope.launch {
            val placeEntities = placeRepository.getAllPlaces()
            val places = placeEntities.map { entity ->
                Place(
                    id = entity.id.toInt(),
                    name = entity.name,
                    description = entity.description,
                    originalImage = entity.originalImage ?: "",
                    detailedDescription = entity.detailedDescription
                )
            }
            _places.value = places
        }
    }
}

