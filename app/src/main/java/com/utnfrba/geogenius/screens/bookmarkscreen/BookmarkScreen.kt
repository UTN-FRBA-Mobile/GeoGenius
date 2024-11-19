package com.utnfrba.geogenius.screens.bookmarkscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.utnfrba.geogenius.appnavigation.Screen
import com.utnfrba.geogenius.model.BookmarkDTO

@Composable
fun BookmarkScreen(bookmarkViewModel: BookmarkViewModel, navController: NavHostController) {
    Column {
        Text(
            text = "My Bookmarks",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
        )
        Spacer(modifier = Modifier.padding(5.dp))
        LoadingBookmarkComposable(bookmarkViewModel, saved = true) {
            if (it.value.isNotEmpty()) {
                LazyColumn {
                    items(it.value) { bookmark ->
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
            } else {
                Text(
                    text="You don't have any bookmarks!",
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
                )
            }
        }
    }
}

@Composable
fun LoadingBookmarkComposable(
    bookmarkViewModel: BookmarkViewModel,
    saved: Boolean,
    composable: @Composable (bookmarks: State<List<BookmarkDTO>>) -> Unit
) {
    val bookmarks =
        if (saved)
            bookmarkViewModel.savedBookmarks.collectAsState()
        else
            bookmarkViewModel.bookmarks.collectAsState()
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
            composable(bookmarks)
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