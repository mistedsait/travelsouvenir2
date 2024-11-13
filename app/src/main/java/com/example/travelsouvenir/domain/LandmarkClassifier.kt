package com.example.travelsouvenir.domain

import android.graphics.Bitmap

interface LandmarkClassifier {
    fun classifiy(bitmap: Bitmap, rotation: Int):List<Classification>
}