package com.utnfrba.geogenius

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.loader.content.AsyncTaskLoader
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.utnfrba.geogenius.appnavigation.GeoGeniusNavGraph
import com.utnfrba.geogenius.appnavigation.Screen
import com.utnfrba.geogenius.database.DB
import com.utnfrba.geogenius.navbar.BottomNavigationBar
import com.utnfrba.geogenius.ui.theme.GeoGeniusTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val con: Context = this
        lifecycleScope.launch {
            DB.buildDB(con)
            println("DB built")
        }.start()
        setContent {
            val navController = rememberNavController()
            GeoGeniusTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigationBar { route ->
                            navController.navigate(route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
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
