package com.utnfrba.geogenius.screens.maps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.utnfrba.geogenius.model.BookmarkDTO
import com.utnfrba.geogenius.model.Coordinate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarComponent(
    bookmarkList: List<BookmarkDTO>,
    onSearchClick: (id: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }

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
            val filtered = bookmarkList.filter { b ->
                if (searchQuery != "") {
                    b.name.uppercase().contains(searchQuery.trim().uppercase())
                } else false
            }
            items(filtered.size) { bookmarkIndex ->
                TextButton(
                    onClick = {
                        onSearchClick(filtered[bookmarkIndex].id)
                        isSearching = false
                    },
                    Modifier.background(Color.Transparent)
                ) {
                    Text(
                        text = filtered[bookmarkIndex].name,
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
}

@Preview
@Composable
fun SearchBarPreview() {
    val bookmarkDTOS = listOf(
        BookmarkDTO(
            id = "1",
            name = "Cabildo",
            description = "Gran cafe",
            longDescription = "Buen lugar para personas fanaticas del cafe con una larga historia",
            address = "Foo 123",
            rating = 4.3,
            images = listOf(),
            coordinates = Coordinate(-34.0923, -53.43556),
            type = "cafe",
        ),
        BookmarkDTO(
            id = "2",
            name = "Las violetas",
            description = "Gran cafe",
            longDescription = "Buen lugar para personas fanaticas del cafe con una larga historia",
            address = "Foo 123",
            rating = 4.3,
            images = listOf(),
            coordinates = Coordinate(34.0923, -53.43556),
            type = "cafe",
        )
    )
    SearchBarComponent(bookmarkDTOS, {})
}