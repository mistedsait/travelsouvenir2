package com.example.travelsouvenir.data

interface PlaceRepository {
    suspend fun insertPlace(place: PlaceEntity)
    suspend fun fetchPlaceInfo(title: String): PlaceEntity
    suspend fun getAllPlaces(): List<PlaceEntity>
    suspend fun getDetailedDescription(placeName: String): String?

}

//PREBACITI DA BUDU INTERFACE, PLACEREPOSITORY IMPLEMENTATION IMPL ,
// SEARCH LANDMARKS;PLACES, LIVE FILTERING, debounce interval, sekunda nakon sto se zavrsi kucanje da izbaci