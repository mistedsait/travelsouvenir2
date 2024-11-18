package com.example.travelsouvenir.pages

import MyPlaceScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.travelsouvenir.pages.landmarks.LandmarkScreen
import com.example.travelsouvenir.viewmodels.PlacesViewModel

@Composable
fun BookmarkScreen(viewModel: PlacesViewModel, navController: NavHostController) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Places", "Landmarks")

    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFFE259),
            Color(0xFFFFA751)
        )
    )

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            backgroundColor = Color.Transparent, // Transparent to allow gradient
            indicator = { tabPositions ->
                Box(
                    Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                        .height(4.dp) // Adjust height of the indicator
                        .background(Color.Gray)
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.body1,
                            color = if (selectedTabIndex == index) Color.White else Color.Gray
                        )
                    },
                    modifier = Modifier
                        .then(
                            if (selectedTabIndex == index) {
                                Modifier.background(brush = gradientBrush)
                            } else {
                                Modifier.background(color = Color.Transparent)
                            }
                        )
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                )


            }
        }

        when (selectedTabIndex) {
            0 -> MyPlaceScreen(viewModel = viewModel, navController = navController)
            1 -> LandmarkScreen(navController = navController)
        }
    }
}


