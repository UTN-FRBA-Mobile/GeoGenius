package com.utnfrba.geogenius

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.utnfrba.geogenius.navbar.BottomNavigationBar
import com.utnfrba.geogenius.navbar.Screen
import com.utnfrba.geogenius.screens.BookmarkScreen
import com.utnfrba.geogenius.screens.bookmarkscreen.PlaceDetailScreen
import com.utnfrba.geogenius.screens.bookmarkscreen.samplePlace
import com.utnfrba.geogenius.screens.bookmarkscreen.samplePlace2
import com.utnfrba.geogenius.screens.filters.FilterScreen
import com.utnfrba.geogenius.screens.maps.MapScreen
import com.utnfrba.geogenius.ui.theme.GeoGeniusTheme

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            navController = rememberNavController()
            NavHost(navController, startDestination = Screen.Map.route) {
                composable(Screen.Filter.route) {
                    FilterScreen()
                }
                composable(Screen.Map.route) {
                    MapScreen(emptyList())
                }
                composable(Screen.Bookmark.route) {
                    BookmarkScreen(listOf(samplePlace, samplePlace2), navController)
                }

                composable(
                    route = Screen.PlaceDetail.route + "/{placeId}",
                    arguments = listOf(navArgument("placeId") { type = NavType.StringType })
                ) { entry ->
                    PlaceDetailScreen(
                        placeId = entry.arguments?.getString("placeId"),
                        navController
                    )
                }
            }
            GeoGeniusTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    BottomNavigationBar(navController)
                }
            }
            intent?.data?.let { uri ->
                if (uri.toString().contains(Screen.PlaceDetail.route)) {
                    val id = uri.getQueryParameter("placeId")
                    // TODO: merge logic with navBar's controller and fix selected icon
                    navController.navigate(Screen.PlaceDetail.route + "/" + id) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
    }
}
