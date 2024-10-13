package com.utnfrba.geogenius.bookmarkscreen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SavedScreen(places: List<PlaceModel>, onPlaceClick: () -> Unit) {
    LazyColumn {
        items(places) { place ->
            PlaceCard(
                place,
                modifier = Modifier,
                onPlaceClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SavedScreenPreview() {
    SavedScreen(
        places = listOf(samplePlace, samplePlace, samplePlace, samplePlace),
        onPlaceClick = {})
}