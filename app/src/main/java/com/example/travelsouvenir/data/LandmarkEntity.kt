package com.example.travelsouvenir.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "landmarks")
data class LandmarkEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val description: String = "",
    @ColumnInfo(name = "original_image") val originalImage: String? = null
)
