package com.utnfrba.geogenius

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.utnfrba.geogenius.ui.theme.GeoGeniusTheme




@Composable
fun BottomNavigationBar() {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Filters", "Map", "Bookmarks")
    val selectedIcons = listOf(Icons.Filled.Home, Icons.Filled.LocationOn, Icons.Filled.Star)
    val unselectedIcons =
        listOf(Icons.Outlined.Home, Icons.Outlined.LocationOn, Icons.Outlined.Star)
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(if (selectedItem == index) selectedIcons[index] else unselectedIcons[index],
                    contentDescription = item)
                   },
                selected = selectedItem == index,
                onClick = { selectedItem = index },
                label = { Text(item) }
            )
        }
        // Ask why not all icons are here
    }
}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    GeoGeniusTheme(darkTheme = true) {
        BottomNavigationBar()
    }
}