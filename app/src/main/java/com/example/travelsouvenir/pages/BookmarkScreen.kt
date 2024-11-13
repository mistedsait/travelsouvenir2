package com.example.travelsouvenir.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.travelsouvenir.R
import com.example.travelsouvenir.ui.theme.TravelSouvenirTheme

@Composable
fun BookmarkScreen(navController: NavHostController,modifier: Modifier = Modifier) {
    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFFE259),
            Color(0xFFFFA751)
        )
    )

    val planet = painterResource(R.drawable.travel_photo)

    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .shadow(8.dp, RoundedCornerShape(8.dp))
                    .height(150.dp)
                    .fillMaxWidth()
                    .clickable {
                        // Navigate to LandmarksPage when clicked
                        navController.navigate(TravelScreen.MyPlaceScreen.route)
                    }
            ) {
                AsyncImage(
                    model = R.drawable.cities,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth(),
                    error = painterResource(R.drawable.paris),
                    fallback = painterResource(R.drawable.paris)
                )

                Row(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Cities",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier
                            .padding(top = 40.dp, start = 20.dp),
                        textAlign = TextAlign.Center
                    )
                }

            }
            Box(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .shadow(8.dp, RoundedCornerShape(8.dp))
                    .height(150.dp)
                    .fillMaxWidth()
                    .clickable {
                        // Navigate to LandmarksPage when clicked
                        navController.navigate(TravelScreen.LandmarksPage.route)
                    }
            ) {
                AsyncImage(
                    model = R.drawable.landmarks,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth(),
                    error = painterResource(R.drawable.paris),
                    fallback = painterResource(R.drawable.paris)
                )

                Row(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Landmarks",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier
                            .padding(top = 40.dp, start = 20.dp),
                        textAlign = TextAlign.Center
                    )
                }

                Text(
                    text = "Routes for visited landmarks",
                    fontSize = 13.sp,
                    lineHeight = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.LightGray,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(bottom = 20.dp, end = 14.dp, start = 20.dp),
                    textAlign = TextAlign.Left
                )
            }
        }
    }
}



