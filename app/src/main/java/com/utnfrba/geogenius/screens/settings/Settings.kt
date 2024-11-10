package com.utnfrba.geogenius.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.utnfrba.geogenius.widget.WidgetSettings

@Composable
fun SettingsMenu() {
    var widgetCount by remember { mutableFloatStateOf(WidgetSettings.getBookmarkAmount().toFloat()) }
    Row(
        modifier = Modifier
            .padding(20.dp)
    ) {
        Column {
            Text("Cantidad de bookmarks en widget")
            Slider(
                value = widgetCount,
                onValueChange = {
                    widgetCount = it
                    WidgetSettings.setBookmarkAmount(it.toInt())
                },
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