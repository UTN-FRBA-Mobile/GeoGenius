package com.utnfrba.geogenius.screens.maps

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarComponent(modifier: Modifier = Modifier) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    val countriesList = listOf("Argentina", "Brazil", "Chile")

    SearchBar(
        query = searchQuery,
        onQueryChange = { searchQuery = it },
        onSearch = { searchQuery = it },
        active = isSearching,
        onActiveChange = {
            isSearching = !isSearching
            if (!isSearching) {
                searchQuery = ""
            }
        },
        placeholder = { Text("Search bookmarks") },
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        LazyColumn {
            val filtered = countriesList.filter {  country ->
                country.uppercase().contains(searchQuery.trim().uppercase())
            }
            items(filtered.size) { country ->
                Text(
                    text = filtered[country],
                    modifier = modifier.padding(
                        start = 8.dp,
                        top = 4.dp,
                        end = 8.dp,
                        bottom = 4.dp
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun SearchBarPreview() {
    SearchBarComponent()
}