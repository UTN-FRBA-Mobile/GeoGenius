package com.utnfrba.geogenius.screens.bookmarkscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import coil3.compose.rememberAsyncImagePainter

@Composable
fun BookmarkDetailScreen(id: String?, navController: NavHostController) {
    val viewModel: BookmarkDetailViewModel = viewModel()
    val bookmark by viewModel.bookmark.collectAsState()

    LaunchedEffect(id) {
        if (id != null && bookmark == null) {
            viewModel.loadBookmark(id)
        }
    }

    when {
        viewModel.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        viewModel.errorMessage != null -> {
            Text(text = "Error: ${viewModel.errorMessage}", color = Color.Red)
        }

        else -> {
            bookmark?.let {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver"
                            )
                        }
                        Text(
                            text = it.name,
                            fontSize = 30.sp,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = it.rating.toString(),
                                fontSize = 30.sp,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = null,
                                tint = Color.hsv(50f, 0.8f, 0.8f),
                                modifier = Modifier
                                    .padding(start = 4.dp)
                                    .size(30.dp)
                            )
                        }
                    }
                    Text(text = it.address, style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow {
                        items(it.images) { imageUrl ->
                            Image(
                                painter = rememberAsyncImagePainter(model = imageUrl),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(200.dp)
                                    .padding(end = 8.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = it.description, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlaceDetailScreenPreview() {
    val navController = NavHostController(LocalContext.current)
    MaterialTheme {
        BookmarkDetailScreen("placeId", navController)
    }
}
