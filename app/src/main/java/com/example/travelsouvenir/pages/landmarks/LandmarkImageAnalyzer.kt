package com.example.travelsouvenir.pages.landmarks

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.travelsouvenir.domain.Classification
import com.example.travelsouvenir.domain.LandmarkClassifier
import com.example.travelsouvenir.pages.centerCrop

class LandmarkImageAnalyzer (
    private val classifier: LandmarkClassifier,
    private val onResults:(List<Classification>)->Unit
): ImageAnalysis.Analyzer {

    private var frameSkipCounter=0

    override fun analyze(image:ImageProxy){
        if(frameSkipCounter % 60==0) {
            val rotationDegrees = image.imageInfo.rotationDegrees
            val bitmap = image
                .toBitmap()
                .centerCrop(321, 321)
            val results = classifier.classifiy(bitmap, rotationDegrees)
            onResults(results)
        }
        frameSkipCounter++
        image.close()
    }
}