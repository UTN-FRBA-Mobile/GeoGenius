package com.utnfrba.geogenius.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.utnfrba.geogenius.appnavigation.Screen
import com.utnfrba.geogenius.screens.bookmarkscreen.BookmarkViewModel
import com.utnfrba.geogenius.screens.bookmarkscreen.BookmarkCard

@Composable
fun BookmarkScreen(bookmarkViewModel: BookmarkViewModel, navController: NavHostController) {
    val bookmarks = bookmarkViewModel.bookmarks.collectAsState()
    val isLoading = bookmarkViewModel.isLoading.collectAsState()
    val errorMessage = bookmarkViewModel.errorMessage.collectAsState()

    when {
        isLoading.value -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        errorMessage.value != null -> {
            Text(text = "Error: ${errorMessage.value}", color = Color.Red)
        }

        else -> {
            LazyColumn {
                items(bookmarks.value) { bookmark ->
                    BookmarkCard(
                        bookmark,
                        modifier = Modifier,
                        onClick = {
                            navController.navigate(Screen.BookmarkDetail.withArgs(bookmark.id)) {
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