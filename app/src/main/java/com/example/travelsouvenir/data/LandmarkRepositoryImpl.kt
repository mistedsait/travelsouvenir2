package com.example.travelsouvenir.data

import com.example.travelsouvenir.data.api.WikipediaApiService
import javax.inject.Inject

class LandmarkRepositoryImpl @Inject constructor(
    private val landmarkDao: LandmarkDao,
    private val wikipediaApiService: WikipediaApiService
) : LandmarkRepository {

    override suspend fun insertLandmark(landmark: LandmarkEntity) {
        val existingPlace = landmarkDao.getLandmarkByName(landmark.name)
        if (existingPlace == null) {
            landmarkDao.insertLandmark(landmark)
        }
    }

    override suspend fun getAllLandmarks(): List<LandmarkEntity> {
        return landmarkDao.getAllLandmarks()
    }
    override suspend fun fetchLandmarkInfo(title: String): LandmarkEntity {
        val response = wikipediaApiService.getPlaceInfo(title)
        return response.toLandmarkEntity()
        //return WikipediaMapper.mapToPlaceEntity(response)
    }
    override suspend fun getLandmarkById(id: Long): LandmarkEntity {
        return landmarkDao.getLandmarkById(id)
    }
}
