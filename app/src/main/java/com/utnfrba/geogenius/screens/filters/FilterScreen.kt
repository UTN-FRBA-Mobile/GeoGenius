package com.utnfrba.geogenius.screens.filters

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FilterScreen(
    filterViewModel: FilterViewModel = viewModel(factory = FilterViewModel.Factory)
) {
    val viewModelState by filterViewModel.uiState.collectAsState()
    val filters = FiltersRepository._filters.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        if (filters.value.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.padding(16.dp)
            ) {
                items(filters.value) { filter ->
                    CheckboxWithLabel(
                        checked = filter.applied,
                        onCheckedChange = {
                            FiltersRepository.changeFilterStatus(filter.id)
                        },
                        label = filter.id
                    )
                }
            }
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