package com.utnfrba.geogenius.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.utnfrba.geogenius.screens.filters.FilterViewModel

@Composable
fun SettingsMenu(filterViewModel: FilterViewModel = viewModel(factory = FilterViewModel.Factory)) {

    val filterState by filterViewModel.uiState.collectAsState()
    Row(
        modifier = Modifier
            .padding(20.dp)
    ) {
        Column {
            Text("Amount of bookmarks in widget")
            Slider(
                value = filterState.widgetCount.toFloat(),
                onValueChange = {
                    filterViewModel.saveWidgetCount(it.toInt())
                },
                modifier = Modifier.padding(10.dp),
                valueRange = 1f..4f,
                steps = 2
            )
            Text(text = filterState.widgetCount.toString())
        }
    }
}

@Preview
@Composable
fun SettingsMenuPreview() {
    SettingsMenu()
}