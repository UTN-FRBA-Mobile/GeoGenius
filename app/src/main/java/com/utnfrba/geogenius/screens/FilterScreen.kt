package com.utnfrba.geogenius.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FilterScreen() {
    Box {
        val marsViewModel: MarsViewModel = viewModel()
        when (val now = marsViewModel.marsUiState) {
            is MarsUiState.Loading -> Text("Loading")
            is MarsUiState.Success -> Text(
                "Photos length: " + now.photos.length.toString()
            )
            is MarsUiState.Error -> Text("Error")
        }
    }
}