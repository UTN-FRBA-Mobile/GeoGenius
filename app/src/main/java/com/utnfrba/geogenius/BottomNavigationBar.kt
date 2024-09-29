package com.utnfrba.geogenius

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.utnfrba.geogenius.ui.theme.GeoGeniusTheme

@Composable
fun BottomNavigationBar() {
    NavigationBar() {
        NavigationBarItem(
            icon = { Icon(Icons.Default.LocationOn, "Filters") },
            selected = true,
            onClick = {},
            label = { Text("Filters") }
        )
        // Ask why not all icons are here
        NavigationBarItem(
            icon = { Icon(Icons.Default.LocationOn, "Location") },
            selected = false,
            onClick = {},
            label = { Text("Location") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.LocationOn, "Bookmarks") },
            selected = false,
            onClick = {},
            label = { Text("Bookmarks") }
        )
    }
}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    GeoGeniusTheme(darkTheme = true) {
        BottomNavigationBar()
    }
}