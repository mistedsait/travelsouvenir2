package com.example.travelsouvenir.pages.guides

//AUGMENTED REALITY

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.travelsouvenir.R
import com.example.travelsouvenir.pages.TravelScreen

@Composable
fun GuidePage(navController: NavHostController) {
    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFFE259),
            Color(0xFFFFA751)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(top = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Welcome to\n" +
                        "TravelSouvenir!",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                style = TextStyle(brush = gradientBrush),
                modifier = Modifier.padding(top = 6.dp)
            )
            Text(
                text = "Discover and Capture Your Journey\n",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                style = TextStyle(brush = gradientBrush),
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "With TravelSouvenir, every destination is a memory. Our app helps you not only capture the moments but also organize, explore, and reminisce about your travels in a whole new way.",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Justify,
                color = Color.Black,
                modifier = Modifier.padding(16.dp)
            )
            PrettyButton(
                text = "Get Started",
                onClick = {
                    navController.navigate(TravelScreen.FirstPage.route) // Navigate to main content
                }
            )
        }
    }
}

@Composable
fun PrettyButton(
    text: String,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(durationMillis = 100)
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(16.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFFFFE259), Color(0xFFFFA751))
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .width(200.dp)
            .height(50.dp)
            .clickable {
                isPressed = true
                onClick()
            }
            .scale(scale)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
    }
}


