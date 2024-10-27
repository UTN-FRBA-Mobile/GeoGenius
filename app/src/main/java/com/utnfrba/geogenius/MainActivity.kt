package com.utnfrba.geogenius

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.utnfrba.geogenius.data.MarsApi
import com.utnfrba.geogenius.navbar.BottomNavigationBar
import com.utnfrba.geogenius.ui.theme.GeoGeniusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeoGeniusTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BottomNavigationBar()
                }
            }
        }
    }
}
