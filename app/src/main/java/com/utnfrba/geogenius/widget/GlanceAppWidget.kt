package com.utnfrba.geogenius.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.Text

class GlanceAppWidget: GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = GeoGeniusWidget()
}

class GeoGeniusWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {

        // In this method, load data needed to render the AppWidget.
        // Use `withContext` to switch to another thread for long running
        // operations.

        provideContent {
            Content()
        }
    }

    @Composable
    private fun Content(modifier: GlanceModifier = GlanceModifier) {
        Column(
            modifier = modifier,
            verticalAlignment = Alignment.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Tus bookmarks", modifier = GlanceModifier.padding(12.dp))
            Box(modifier) {
                CardRow()
            }
            Box(modifier){
                CardRow()
            }


        }
    }
}

@Composable
private fun CardRow(modifier: GlanceModifier = GlanceModifier){
    Row(modifier.padding(5.dp)) {
        Button("Cabildo", onClick = {}, modifier)
    }
}
