package com.utnfrba.geogenius.appnavigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.utnfrba.geogenius.screens.BookmarkScreen
import com.utnfrba.geogenius.screens.bookmarkscreen.BookmarkViewModel
import com.utnfrba.geogenius.screens.bookmarkscreen.BookmarkDetailScreen
import com.utnfrba.geogenius.screens.filters.FilterScreen
import com.utnfrba.geogenius.screens.maps.MapScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier

@Composable
fun GeoGeniusNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Map.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(Screen.Filter.route) { FilterScreen() }
        composable(Screen.Map.route) { MapScreen(emptyList()) }
        composable(Screen.Bookmark.route) {
            val bookmarkViewModel: BookmarkViewModel = viewModel()
            BookmarkScreen(bookmarkViewModel, navController)
        }
        composable(
            route = Screen.BookmarkDetail.route + "/{placeId}",
            arguments = listOf(navArgument("placeId") { type = NavType.StringType })
        ) { entry ->
            BookmarkDetailScreen(
                id = entry.arguments?.getString("placeId"),
                navController = navController
            )
        }
    }
}
