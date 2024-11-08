package com.utnfrba.geogenius

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.utnfrba.geogenius.navbar.BottomNavigationBar
import com.utnfrba.geogenius.navbar.Screen
import com.utnfrba.geogenius.ui.theme.GeoGeniusTheme

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            navController = rememberNavController()
            GeoGeniusTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    BottomNavigationBar(navController)
                }
            }
            intent?.data?.let { uri ->
                if (uri.toString().contains(Screen.Bookmark.toString())) {
                    // Navigate to the Bookmark screen
                    navController.navigate(Screen.Bookmark.toString())
                }
            }
        }
    }
}
