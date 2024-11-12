package com.utnfrba.geogenius.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.utnfrba.geogenius.navbar.Screen
import com.utnfrba.geogenius.screens.bookmarkscreen.BookmarkViewModel
import com.utnfrba.geogenius.screens.bookmarkscreen.PlaceCard

@Composable
fun BookmarkScreen(bookmarkViewModel: BookmarkViewModel, navController: NavHostController) {
    val places = bookmarkViewModel.bookmarks
    val isLoading = bookmarkViewModel.isLoading
    val errorMessage = bookmarkViewModel.errorMessage

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        if (errorMessage != null) {
            Text(text = "Error: $errorMessage", color = Color.Red)
        } else {
            LazyColumn {
                items(places) { place ->
                    PlaceCard(
                        place,
                        modifier = Modifier,
                        onClick = {
                            navController.navigate(Screen.PlaceDetail.withArgs(place.id)) {
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
    }
}

@Preview(showBackground = true)
@Composable
fun SavedScreenPreview() {
    val navController = NavHostController(LocalContext.current)
    val bookmarkViewModel: BookmarkViewModel = viewModel()
    BookmarkScreen(bookmarkViewModel, navController)
}