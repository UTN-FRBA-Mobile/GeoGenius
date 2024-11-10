package com.utnfrba.geogenius.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.utnfrba.geogenius.navbar.Screen
import com.utnfrba.geogenius.screens.bookmarkscreen.PlaceCard
import com.utnfrba.geogenius.screens.bookmarkscreen.PlaceModel
import com.utnfrba.geogenius.screens.bookmarkscreen.sampleList

@Composable
fun BookmarkScreen(places: List<PlaceModel>, navController: NavHostController) {
    LazyColumn {
        items(places) { place ->
            PlaceCard(
                place,
                modifier = Modifier,
                onClick = {
                    navController.navigate(Screen.PlaceDetail.withArgs(place.id)){
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SavedScreenPreview() {
    val navController = NavHostController(LocalContext.current)
    BookmarkScreen(sampleList, navController)
}