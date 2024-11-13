package com.utnfrba.geogenius.screens.filters

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.utnfrba.geogenius.screens.settings.SettingsMenu

private const val USER_PREFERENCES_NAME = "filter_preferences"

private val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)

@Composable
fun FilterScreen() {
    val filterViewModel: FilterViewModel = ViewModelProvider(this,
        FilterViewModelFactory(FilterDataStore(dataStore))
    )[FilterViewModel::class.java]
    val cafeCheckState by filterViewModel.cafeState.collectAsState()
    val museumCheckState by filterViewModel.museumState.collectAsState()
    val parkCheckState by filterViewModel.parkState.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            CheckboxWithLabel(
                checked = cafeCheckState,
                onCheckedChange = {
                    filterViewModel.setCafeStatus(it)
                },
                label = "Cafés"
            )

            CheckboxWithLabel(
                checked = museumCheckState,
                onCheckedChange = {
                    filterViewModel.setMuseumStatus(it)
                },
                label = "Museos"
            )

            CheckboxWithLabel(
                checked = parkCheckState,
                onCheckedChange = {
                    filterViewModel.setParkStatus(it)
                },
                label = "Parques"
            )

            SettingsMenu()
        }
    }


    // Network request
//    val bookmarkViewModel: BookmarkViewModel = viewModel()
//    when (val now = bookmarkViewModel.bookmarkUiState) {
//        is BookmarkUiState.Loading -> Text("Loading")
//        is BookmarkUiState.Success -> Text(
//            "Titulo: " + now.photos[0].name
//        )
//        is BookmarkUiState.Error -> Text("Error")
//    }

}

@Composable
fun CheckboxWithLabel(checked: Boolean, onCheckedChange: (Boolean) -> Unit, label: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Text(
            text = label,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FilterScreenPreview() {
    FilterScreen()
}