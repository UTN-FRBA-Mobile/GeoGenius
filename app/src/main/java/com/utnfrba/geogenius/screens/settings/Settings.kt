package com.utnfrba.geogenius.screens.settings

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderState

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsMenu() {
    Row {
        Text("Elegir")
        Slider(SliderState(valueRange = 0f..5f, steps = 5))
        // here the filters? or max widget count
    }
}

@Preview
@Composable
fun SettingsMenuPreview() {
    SettingsMenu()
}