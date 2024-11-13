package com.example.travelsouvenir.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PlaceEntity::class, LandmarkEntity::class], version = 11, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao
    abstract fun landmarkDao(): LandmarkDao
}
