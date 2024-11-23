package com.utnfrba.geogenius.navbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.utnfrba.geogenius.R
import com.utnfrba.geogenius.appnavigation.Screen

data class BottomNavigationBarItem(
    val label: String = "",
    val filledIcon: ImageVector = Icons.Default.Home,
    val outlinedIcon: ImageVector = Icons.Default.Home,
    val route: String = ""
) {
    @Composable
    fun getBottomNavigationItems(): List<BottomNavigationBarItem> {
        return listOf(
            BottomNavigationBarItem(
                label = stringResource(R.string.filterLabel),
                filledIcon = ImageVector.vectorResource(R.drawable.baseline_filter_alt_24),
                outlinedIcon = ImageVector.vectorResource(R.drawable.outline_filter_alt_24),
                route = Screen.Filter.route
            ),
            BottomNavigationBarItem(
                label = stringResource(R.string.mapLabel),
                filledIcon = Icons.Filled.LocationOn,
                outlinedIcon = Icons.Outlined.LocationOn,
                route = Screen.Map.route
            ),
            BottomNavigationBarItem(
                label = stringResource(R.string.bookmarkLabel),
                filledIcon = ImageVector.vectorResource(R.drawable.baseline_bookmark_24),
                outlinedIcon = ImageVector.vectorResource(R.drawable.baseline_bookmark_border_24),
                route = Screen.Bookmark.route
            ),
        )
    }
}