package com.example.travelsouvenir.pages

import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travelsouvenir.data.LandmarkEntity
import com.example.travelsouvenir.data.TfLiteLandmarkClassifier
import com.example.travelsouvenir.domain.Classification
import com.example.travelsouvenir.pages.guides.PrettyButton
import com.example.travelsouvenir.pages.landmarks.LandmarkImageAnalyzer
import com.example.travelsouvenir.viewmodels.LandmarkViewModel

@Composable
fun ClassificationPage(
    viewModel: LandmarkViewModel = hiltViewModel() // Injecting LandmarkViewModel
) {
    val context = LocalContext.current // Retrieve the context

    var classifications by remember { mutableStateOf(emptyList<Classification>()) }

    val analyzer = remember {
        LandmarkImageAnalyzer(
            classifier = TfLiteLandmarkClassifier(
                context = context
            ),
            onResults = { classifications = it }
        )
    }

    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_ANALYSIS)
            setImageAnalysisAnalyzer(
                ContextCompat.getMainExecutor(context),
                analyzer
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreview(controller, Modifier.fillMaxSize())

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            classifications.forEach {
                Text(
                    text = it.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            PrettyButton(
                text = "Save Landmark",
                onClick = {
                    // Example values; replace with actual classification data
                    val landmark = LandmarkEntity(
                        name = classifications.firstOrNull()?.name ?: "Unknown Landmark", // Replace with actual classification name

                    )

                    // Save landmark in database via ViewModel
                    viewModel.loadLandmarks(listOf(landmark.name))
                }
            )
        }
    }
}
