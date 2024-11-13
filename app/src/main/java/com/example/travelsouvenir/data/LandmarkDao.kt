package com.example.travelsouvenir.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LandmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLandmark(landmark: LandmarkEntity)

    @Query("SELECT * FROM landmarks WHERE name = :landmarkName LIMIT 1")
    suspend fun getLandmarkByName(landmarkName: String): LandmarkEntity?

    @Query("SELECT * FROM landmarks")
    suspend fun getAllLandmarks(): List<LandmarkEntity>

    @Query("SELECT * FROM landmarks WHERE id = :id")
    suspend fun getLandmarkById(id: Long): LandmarkEntity
}

