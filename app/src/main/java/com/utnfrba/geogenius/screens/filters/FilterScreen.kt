package com.utnfrba.geogenius.screens.filters

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.utnfrba.geogenius.R
import com.utnfrba.geogenius.screens.settings.SettingsMenu

@Composable
fun FilterScreen(
    filterViewModel: FilterViewModel = viewModel(factory = FilterViewModel.Factory)
) {
    val viewModelState by filterViewModel.uiState.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            CheckboxWithLabel(
                checked = viewModelState.cafeChecked,
                onCheckedChange = {
                    filterViewModel.saveFilterValue(it, PreferencesKeys.CAFE_CHECKED)
                },
                label = stringResource(R.string.cafeFilter)
            )

            CheckboxWithLabel(
                checked = viewModelState.museumChecked,
                onCheckedChange = {
                    filterViewModel.saveFilterValue(it, PreferencesKeys.MUSEUM_CHECKED)
                },
                label = stringResource(R.string.museumFilter)
            )

            CheckboxWithLabel(
                checked = viewModelState.parkChecked,
                onCheckedChange = {
                    filterViewModel.saveFilterValue(it, PreferencesKeys.PARK_CHECKED)
                },
                label = stringResource(R.string.parkFilter)
            )

            SettingsMenu(filterViewModel)
        }
    }
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