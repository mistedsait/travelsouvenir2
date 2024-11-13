package com.example.travelsouvenir.pages.landmarks

data class Landmark(
    val id: Int,
    val name: String,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val description: String,
    val originalImage: String, )
