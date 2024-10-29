package com.utnfrba.geogenius.navbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.utnfrba.geogenius.R

sealed class Screen(val route: String) {
    data object Filter : Screen("filter_route")
    data object Map : Screen("map_route")
    data object Bookmark : Screen("bookmark_route")
    data object PlaceDetail : Screen("place_detail")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg -> append("/$arg") }
        }
    }
}

data class BottomNavigationBarItem(
    val label: String = "",
    val filledIcon: ImageVector = Icons.Default.Home,
    val outlinedIcon: ImageVector = Icons.Default.Home,
    val route: Screen = Screen.Filter
) {
    @Composable
    fun getBottomNavigationItems(): List<BottomNavigationBarItem> {
        return listOf(
            BottomNavigationBarItem(
                label = "Filters",
                filledIcon = ImageVector.vectorResource(R.drawable.baseline_filter_alt_24),
                outlinedIcon = ImageVector.vectorResource(R.drawable.outline_filter_alt_24),
                route = Screen.Filter
            ),
            BottomNavigationBarItem(
                label = "Map",
                filledIcon = Icons.Filled.LocationOn,
                outlinedIcon = Icons.Outlined.LocationOn,
                route = Screen.Map
            ),
            BottomNavigationBarItem(
                label = "Bookmarks",
                filledIcon = ImageVector.vectorResource(R.drawable.baseline_bookmark_24),
                outlinedIcon = ImageVector.vectorResource(R.drawable.baseline_bookmark_border_24),
                route = Screen.Bookmark
            ),
        )
    }
}