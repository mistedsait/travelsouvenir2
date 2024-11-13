package com.example.travelsouvenir.data



interface LandmarkRepository {
    suspend fun insertLandmark(landmark: LandmarkEntity)
    suspend fun getAllLandmarks(): List<LandmarkEntity>
    suspend fun fetchLandmarkInfo(title: String): LandmarkEntity
    suspend fun getLandmarkById(id: Long): LandmarkEntity
}
