package com.example.travelsouvenir.pages.guides




import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.travelsouvenir.R
import com.example.travelsouvenir.pages.TravelScreen
import com.example.travelsouvenir.ui.theme.TravelSouvenirTheme

@Composable
fun SixthGuidePage(navController: NavHostController) {
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
                    .height(200.dp)
                    .width(200.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.travel_photo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .shadow(8.dp, RoundedCornerShape(16.dp))


                )
            }
            Text(
                text = "Start exploring with Travel Souvenir and let us help you turn your travels into lasting memories! \uD83C\uDF0F\uD83D\uDCF8✨\n",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                style = TextStyle(brush = gradientBrush),
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
            )

           
            PrettyButton(
                text = "Start Exploring",
                onClick = {navController.navigate(TravelScreen.LandingPage.route)}
            )
        }
    }
}



