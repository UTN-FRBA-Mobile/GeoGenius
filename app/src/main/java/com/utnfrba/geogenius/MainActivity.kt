package com.utnfrba.geogenius

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.utnfrba.geogenius.appnavigation.GeoGeniusNavGraph
import com.utnfrba.geogenius.appnavigation.Screen
import com.utnfrba.geogenius.navbar.BottomNavigationBar
import com.utnfrba.geogenius.ui.theme.GeoGeniusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            GeoGeniusTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigationBar(navController)
                    }
                ) { paddingValues ->
                    GeoGeniusNavGraph(navController = navController, paddingValues = paddingValues)
                }
            }
            LaunchedEffect("widget") {
                intent?.data?.let { uri ->
                    if (uri.toString().contains(Screen.BookmarkDetail.route)) {
                        val id = uri.getQueryParameter("placeId")
                        navController.navigate(Screen.BookmarkDetail.route + "/" + id)
                    }
                }
            }
        }
    }
}
