package com.utnfrba.geogenius.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderState

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsMenu() {
    Row(modifier = Modifier.background(Color.White)) {
        Text("Elegir")
        Box(){
            Slider(SliderState(valueRange = 1f..4f, steps = 4))
        }
        Text("a")
        // here the filters? or max widget count
    }
}

@Preview
@Composable
fun SettingsMenuPreview() {
    SettingsMenu()
}