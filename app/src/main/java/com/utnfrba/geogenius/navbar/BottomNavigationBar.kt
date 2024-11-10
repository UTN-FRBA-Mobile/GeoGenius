package com.utnfrba.geogenius.navbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.utnfrba.geogenius.screens.BookmarkScreen
import com.utnfrba.geogenius.screens.bookmarkscreen.PlaceDetailScreen
import com.utnfrba.geogenius.screens.bookmarkscreen.samplePlace
import com.utnfrba.geogenius.screens.bookmarkscreen.samplePlace2
import com.utnfrba.geogenius.screens.filters.FilterScreen
import com.utnfrba.geogenius.screens.maps.MapScreen
import com.utnfrba.geogenius.ui.theme.GeoGeniusTheme

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    var selectedItem by remember { mutableIntStateOf(1) }

    Scaffold(
        bottomBar = {
            if (currentRoute(navController) != Screen.PlaceDetail.route) {
                NavigationBar {
                    BottomNavigationBarItem().getBottomNavigationItems()
                        .forEachIndexed { index, item ->
                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        if (selectedItem == index) item.filledIcon else item.outlinedIcon,
                                        contentDescription = item.label
                                    )
                                },
                                selected = selectedItem == index,
                                onClick = {
                                    selectedItem = index
                                    if (navController.currentDestination?.route != item.route) {
                                        navController.navigate(item.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = false
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                },
                                label = { Text(item.label) }
                            )
                        }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController,
            startDestination = Screen.Map.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Filter.route) {
                FilterScreen()
            }
            composable(Screen.Map.route) {
                MapScreen()
            }
            composable(Screen.Bookmark.route) {
                BookmarkScreen(listOf(samplePlace, samplePlace2), navController)
            }

            composable(
                route = Screen.PlaceDetail.route + "/{placeId}",
                arguments = listOf(navArgument("placeId") { type = NavType.StringType })
            ) { entry ->
                PlaceDetailScreen(placeId = entry.arguments?.getString("placeId"), navController)
//                selectedItem = 2
            }
        }
    }

}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.arguments?.getString(Screen.PlaceDetail.route)
}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    val navC = rememberNavController()
    GeoGeniusTheme(darkTheme = true) {
        BottomNavigationBar(navC)
    }
}
