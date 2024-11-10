package com.utnfrba.geogenius.screens.filters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.utnfrba.geogenius.screens.settings.SettingsMenu

@Composable
fun FilterScreen() {

    var cafeChecked by remember { mutableStateOf(FilterSettings.getCafeChecked()) }
    var museumChecked by remember { mutableStateOf(FilterSettings.getMuseumChecked()) }
    var parkChecked by remember { mutableStateOf(FilterSettings.getParkChecked()) }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            CheckboxWithLabel(
                checked = cafeChecked,
                onCheckedChange = {
                    cafeChecked = it
                    FilterSettings.setCafeChecked(it)
                },
                label = "Cafés"
            )

            CheckboxWithLabel(
                checked = museumChecked,
                onCheckedChange = {
                    museumChecked = it
                    FilterSettings.setMuseumChecked(it)
                },
                label = "Museos"
            )

            CheckboxWithLabel(
                checked = parkChecked,
                onCheckedChange = {
                    parkChecked = it
                    FilterSettings.setParkChecked(it)
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