package com.utnfrba.geogenius.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.utnfrba.geogenius.bookmarkscreen.PlaceCard
import com.utnfrba.geogenius.bookmarkscreen.PlaceModel
import com.utnfrba.geogenius.bookmarkscreen.samplePlace

@Composable
fun BookmarkScreen(places: List<PlaceModel>) {
    LazyColumn {
        items(places) { place ->
            PlaceCard(
                place,
                modifier = Modifier,
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SavedScreenPreview() {
    BookmarkScreen(listOf(samplePlace, samplePlace))
}