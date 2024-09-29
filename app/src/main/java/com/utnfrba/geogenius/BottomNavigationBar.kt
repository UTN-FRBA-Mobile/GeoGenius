package com.utnfrba.geogenius

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.utnfrba.geogenius.ui.theme.GeoGeniusTheme

@Composable
fun BottomNavigationBar() {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Filters", "Map", "Bookmarks")
    val selectedIcons = listOf(
        ImageVector.vectorResource(R.drawable.baseline_filter_alt_24),
        Icons.Filled.LocationOn,
        ImageVector.vectorResource(R.drawable.baseline_bookmark_24)
    )
    val unselectedIcons =
        listOf(
            ImageVector.vectorResource(R.drawable.outline_filter_alt_24),
            Icons.Outlined.LocationOn,
            ImageVector.vectorResource(R.drawable.baseline_bookmark_border_24)
        )
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