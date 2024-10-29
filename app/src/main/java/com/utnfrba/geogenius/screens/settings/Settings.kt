package com.utnfrba.geogenius.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SettingsMenu() {
    var widgetCount by remember { mutableFloatStateOf(1f) }
    Row(modifier = Modifier
        .background(Color.White)
        .padding(20.dp)) {
        Column {
            Text("Elegir cantidad de items en widget")
            Slider(
                value = widgetCount,
                onValueChange = { widgetCount = it },
                modifier = Modifier.padding(10.dp),
                valueRange = 1f..4f,
                steps = 2
            )
            Text(text = widgetCount.toInt().toString())
        }
    }
}

@Preview
@Composable
fun SettingsMenuPreview() {
    SettingsMenu()
}