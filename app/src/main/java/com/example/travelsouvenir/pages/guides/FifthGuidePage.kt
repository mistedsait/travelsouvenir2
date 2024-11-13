package com.example.travelsouvenir.pages.guides




import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.travelsouvenir.R
import com.example.travelsouvenir.pages.TravelScreen
import com.example.travelsouvenir.ui.theme.TravelSouvenirTheme

@Composable
fun FifthGuidePage(navController: NavHostController) {
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
                .padding(top=30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .height(75.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(R.drawable.scan),
                    contentDescription = null,
                    modifier = Modifier
                        .height(75.dp)
                        .fillMaxWidth()

                )
            }
            Text(
                text = "Discover Landmarks Instantly with AI\n",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                style = TextStyle(brush = gradientBrush),
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = "Use your camera to identify landmarks around you!\n" +
                        " Our AI-powered feature recognizes famous sights and provides you with their names and details, adding context and enhancing your travel experience in real-time.",
                fontSize =14.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = Color.Black,
                modifier = Modifier.padding(10.dp)
            )
            PrettyButton(
                text = "Next",
                onClick = {navController.navigate(TravelScreen.SixthGuidePage.route)}
            )
        }
    }
}






