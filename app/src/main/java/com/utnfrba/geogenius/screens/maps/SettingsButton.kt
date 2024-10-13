package com.utnfrba.geogenius.screens.maps

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SettingsButton() {
    Image(
        Icons.Default.Person,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        colorFilter = ColorFilter.tint(Color.White),
        modifier = Modifier.clickable { println("Button Clicked!") }
            .size(64.dp)
            .clip(CircleShape)
            .aspectRatio(1f)
            .background(Color.Black),
        )
}

@Preview
@Composable
fun SettingsButtonPreview() {
    SettingsButton()
}