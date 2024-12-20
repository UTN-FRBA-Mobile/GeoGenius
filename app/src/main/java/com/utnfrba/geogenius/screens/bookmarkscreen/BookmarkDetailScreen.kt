package com.utnfrba.geogenius.screens.bookmarkscreen

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.utnfrba.geogenius.R

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BookmarkDetailScreen(
    id: String,
    onReturnClick: () -> Unit,
    bookmarkViewModel: BookmarkViewModel
) {
    val viewModel: BookmarkDetailViewModel = viewModel()
    val bookmark by viewModel.bookmark.collectAsState()
    var saveOrDeleteIcon by remember { mutableStateOf(Icons.Filled.Add) }

    LaunchedEffect(id) {
        if (id != "" && bookmark == null) {
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
            Text(text = stringResource(R.string.checkConnection))
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
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onReturnClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.returnArrow)
                            )
                        }
                        Text(
                            text = it.name,
                            fontSize = 30.sp,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(start = 30.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(text = it.address, style = MaterialTheme.typography.bodyLarge)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = it.rating.toString(),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = null,
                                tint = Color.hsv(50f, 0.8f, 0.8f),
                                modifier = Modifier
                                    .padding(start = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    val pagerState = rememberPagerState()
                    HorizontalPager(
                        count = it.images.size,
                        state = pagerState,
                        modifier = Modifier.fillMaxWidth()
                    ) { page ->
                        AsyncImage(
                            model = it.images[page],
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .padding(end = 8.dp)
                        )
                    }
                    if (it.images.size > 1) {
                        Spacer(modifier = Modifier.height(4.dp))

                        HorizontalPagerIndicator(
                            pagerState = pagerState,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(8.dp),
                            activeColor = MaterialTheme.colorScheme.primary,
                            inactiveColor = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = it.longDescription, style = MaterialTheme.typography.bodyLarge)
                    val onClickAction: () -> Unit
                    if (bookmarkViewModel.isSaved(id)) {
                        onClickAction = {
                            bookmarkViewModel.deleteBookmark(it)
                            saveOrDeleteIcon = Icons.Filled.Add
                        }
                        saveOrDeleteIcon = Icons.Filled.Delete
                    } else {
                        onClickAction = {
                            bookmarkViewModel.addBookmark(it)
                            saveOrDeleteIcon = Icons.Filled.Delete
                        }
                        saveOrDeleteIcon = Icons.Filled.Add
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    FilledIconButton(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(30.dp)
                            .align(Alignment.End),
                        onClick = onClickAction,
                        content = {
                            Icon(
                                imageVector = saveOrDeleteIcon,
                                contentDescription = ""
                            )
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlaceDetailScreenPreview() {
    MaterialTheme {
        BookmarkDetailScreen("placeId", {}, viewModel())
    }
}
