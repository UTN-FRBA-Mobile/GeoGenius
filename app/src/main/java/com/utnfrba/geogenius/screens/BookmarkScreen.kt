package com.utnfrba.geogenius.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.utnfrba.geogenius.navbar.Screen
import com.utnfrba.geogenius.screens.bookmarkscreen.PlaceCard
import com.utnfrba.geogenius.screens.bookmarkscreen.PlaceModel
import com.utnfrba.geogenius.screens.bookmarkscreen.samplePlace

@Composable
fun BookmarkScreen(places: List<PlaceModel>, navController: NavHostController) {
    LazyColumn {
        items(places) { place ->
            PlaceCard(
                place,
                modifier = Modifier,
                onClick = {
                    navController.navigate(Screen.PlaceDetail(place.id).route)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SavedScreenPreview() {
    val navController = NavHostController(LocalContext.current)
    BookmarkScreen(listOf(samplePlace, samplePlace), navController)
}