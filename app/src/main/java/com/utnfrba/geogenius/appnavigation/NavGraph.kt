package com.utnfrba.geogenius.appnavigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.utnfrba.geogenius.screens.bookmarkscreen.BookmarkDetailScreen
import com.utnfrba.geogenius.screens.bookmarkscreen.BookmarkScreen
import com.utnfrba.geogenius.screens.bookmarkscreen.BookmarkViewModel
import com.utnfrba.geogenius.screens.filters.FilterScreen
import com.utnfrba.geogenius.screens.filters.FilterViewModel
import com.utnfrba.geogenius.screens.maps.MapScreen

@Composable
fun GeoGeniusNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues = PaddingValues(),
) {
    val bookmarkViewModel: BookmarkViewModel = viewModel()
    val filterViewModel: FilterViewModel = viewModel(factory = FilterViewModel.Factory)
    NavHost(
        navController = navController,
        startDestination = Screen.Map.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(Screen.Filter.route) { FilterScreen(filterViewModel) }
        composable(Screen.Map.route) {
            MapScreen(
                navController,
                bookmarkViewModel,
                filterViewModel
            )
        }
        composable(Screen.Bookmark.route) {
            BookmarkScreen(bookmarkViewModel, navController)
        }
        composable(
            route = Screen.BookmarkDetail.route + "/{placeId}",
            arguments = listOf(navArgument("placeId") { type = NavType.StringType })
        ) { entry ->
            val placeId = entry.arguments?.getString("placeId") ?: ""
            BookmarkDetailScreen(
                id = placeId,
                onReturnClick = { navController.popBackStack() },
                bookmarkViewModel = bookmarkViewModel,
            )
        }
    }
}
