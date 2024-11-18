package com.example.travelsouvenir.pages

import MyPlaceScreen
import android.Manifest
import android.location.Location
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.travelsouvenir.R
import com.example.travelsouvenir.pages.guides.FifthGuidePage
import com.example.travelsouvenir.pages.guides.FirstGuidePage
import com.example.travelsouvenir.pages.guides.FourthGuidePage
import com.example.travelsouvenir.pages.guides.GuidePage
import com.example.travelsouvenir.pages.guides.SecondGuidePage
import com.example.travelsouvenir.pages.guides.SixthGuidePage
import com.example.travelsouvenir.pages.guides.ThirdGuidePage
import com.example.travelsouvenir.pages.landmarks.LandmarkScreen
import com.example.travelsouvenir.utils.LocationHelper
import com.example.travelsouvenir.viewmodels.GalleryViewModel
import com.example.travelsouvenir.viewmodels.PlacesViewModel
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import java.io.File
import com.example.travelsouvenir.viewmodels.LandmarkViewModel
import com.example.travelsouvenir.viewmodels.LaunchViewModel
import com.example.travelsouvenir.viewmodels.LaunchViewModelFactory
import com.google.android.gms.maps.model.LatLng

enum class TravelScreen(val route: String) {
    LandingPage("LandingPage"),
    MyPlaceScreen("MyPlaceScreen"),
    GalleryScreen("GalleryScreen/{placeName}"),
    ClassificationPage("ClassificationPage"),
    GuidePage("GuidePage"),
    FourthGuidePage("FourthGuidePage"),
    FifthGuidePage("FifthGuidePage"),
    SixthGuidePage("SixthGuidePage"),
    FirstPage("FirstPage"),
    SecondPage("SecondPage"),
    ThirdPage("ThirdPage"),
    LandmarksPage("LandmarksPage"),
    BookmarkScreen("BookmarkScreen"),
    GoogleMapScreen("GoogleMapScreen")
}
@Composable
fun MyScreenContent(viewModel: PlacesViewModel, deepLinkPlaceName: String? = null, ) {
    val context = LocalContext.current
    val locationHelper = remember { LocationHelper(context, viewModel) }
    val logo = painterResource(R.drawable.paper_plane)
    val home = painterResource(R.drawable.homeor)
    val profile = painterResource(R.drawable.profile)
    val scan= painterResource(R.drawable.scan)
    val book = painterResource(R.drawable.book)
    val camera = painterResource(R.drawable.camera)
    val navController: NavHostController = rememberNavController()
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val locationState = remember { mutableStateOf<Location?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    val imageUri = remember { mutableStateOf<Uri?>(null) }

    val launchViewModel: LaunchViewModel = viewModel(
        factory = LaunchViewModelFactory(context)
    )
    val startDestination = if (launchViewModel.isFirstLaunch) {
        TravelScreen.GuidePage.route
    } else {
        TravelScreen.LandingPage.route
    }


    LaunchedEffect(Unit) {
        viewModel.loadPlacesFromDatabase()
        isLoading.value = false
    }

    val locationRequest = remember {
        LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 10000L
        ).apply { setMinUpdateIntervalMillis(5000L) }.build()
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            val cameraGranted = permissions[Manifest.permission.CAMERA] ?: false
            val locationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false

            if (cameraGranted && locationGranted) {
                locationHelper.startLocationUpdates(fusedLocationClient, locationRequest, locationState)
            } else {
                Log.d("Permission", "Camera or location permission denied")
            }
        }
    )
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            Log.d("Camera", "Image saved to: ${imageUri.value}")
            locationHelper.getCityNameFromLocation(fusedLocationClient, imageUri.value!!)
        }
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )

        val imageFile = File(context.filesDir, "temp_image.jpg")
        imageUri.value = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", imageFile)
    }

    // Navigate to GalleryScreen if deepLinkPlaceName is provided
    LaunchedEffect(deepLinkPlaceName) {
        deepLinkPlaceName?.let { name ->
            navController.navigate(TravelScreen.GalleryScreen.route.replace("{placeName}", name))
        }
    }

    Scaffold(
        topBar = { Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) { Image(logo, null,
            Modifier
                .size(88.dp)
                .padding(top = 30.dp)) } },
        bottomBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .height(80.dp)
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.BottomCenter
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(home, "Home",
                        Modifier
                            .size(55.dp)
                            .clickable { navController.navigate(TravelScreen.LandingPage.route) })
                    Image(camera, "Camera",
                        Modifier
                            .size(55.dp)
                            .clickable { cameraLauncher.launch(imageUri.value) })
                    Image(book, "Book",
                        Modifier
                            .size(50.dp)
                            .clickable { navController.navigate(TravelScreen.BookmarkScreen.route) })
                    Image(scan, "Profile",
                        Modifier
                            .size(55.dp)
                            .clickable { navController.navigate(TravelScreen.ClassificationPage.route) })
                }
            }
        }
    )

    { paddingValues ->


        NavHost(
            navController = navController,
            startDestination = startDestination,
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            composable(
                TravelScreen.LandingPage.route,
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) }
            ) { LandingPage() }
            composable(
                TravelScreen.BookmarkScreen.route,
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) }
            ) { BookmarkScreen(viewModel,navController) }
            composable(
                TravelScreen.GuidePage.route,
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) }
            ) { GuidePage(navController) }
            composable(
                TravelScreen.FirstPage.route,
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) }
            ) { FirstGuidePage(navController) }
            composable(
                TravelScreen.SecondPage.route,
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) }
            ) { SecondGuidePage(navController) }
            composable(
                TravelScreen.ThirdPage.route,
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) }
            ) { ThirdGuidePage(navController) }
            composable(
                TravelScreen.FourthGuidePage.route,
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) }
            ) { FourthGuidePage(navController) }
            composable(
                TravelScreen.FifthGuidePage.route,
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) }
            ) { FifthGuidePage(navController) }
            composable(
                TravelScreen.SixthGuidePage.route,
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) }
            ) { SixthGuidePage(navController) }
            composable(
                TravelScreen.LandmarksPage.route,
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) }
            ) {
                val viewModel: LandmarkViewModel = hiltViewModel()
                LandmarkScreen(navController, viewModel = viewModel) }

            composable(
                TravelScreen.MyPlaceScreen.route,
                enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) },
                exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }) }
            ){
                MyPlaceScreen(viewModel, navController)
            }


            composable(
                TravelScreen.ClassificationPage.route,
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) }
            ) { ClassificationPage() }
            composable(
                route = TravelScreen.GalleryScreen.route,
                arguments = listOf(navArgument("placeName") { type = NavType.StringType }),
                deepLinks = listOf(navDeepLink { uriPattern = "myapp://gallery/{placeName}" }),
                enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) },
                exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }) }
            ) { backStackEntry ->
                val placeName = backStackEntry.arguments?.getString("placeName") ?: ""
                val detailedDescription = viewModel.places.value?.find { it.name == placeName }?.detailedDescription ?: ""
                val galleryViewModel: GalleryViewModel = hiltViewModel()

                LaunchedEffect(placeName, detailedDescription) {
                    galleryViewModel.loadGalleryData(emptyList(), detailedDescription, placeName)
                }

                GalleryScreen(galleryViewModel)
            }
            composable(
                route = "GoogleMapsScreen/{landmarkName}"
            ) { backStackEntry ->
                val landmarkName = backStackEntry.arguments?.getString("landmarkName") ?: ""

                // Fetch the LatLng for the landmark name, defaulting to LatLng(0.0, 0.0) if not found
                val landmarkLocation = locationHelper.getLatLngFromLandmarkName(landmarkName, context) as? LatLng ?: LatLng(0.0, 0.0)

                LandmarkMapScreen(
                    landmarkName = landmarkName,
                    landmarkLocation = landmarkLocation,
                    apiKey = context.getString(R.string.google_maps_api_key)
                )
            }


        }
    }

    locationState.value?.let { Log.d("Location", "Lat: ${it.latitude}, Lng: ${it.longitude}") }
}
