package com.utnfrba.geogenius.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.components.FilledButton
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.components.TitleBar
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.preview.ExperimentalGlancePreviewApi
import com.utnfrba.geogenius.R
import com.utnfrba.geogenius.model.BookmarkDTO
import com.utnfrba.geogenius.model.Coordinates
import kotlin.math.roundToInt

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
        Scaffold (
            modifier = modifier,
            backgroundColor = GlanceTheme.colors.widgetBackground,
            titleBar = {
                TitleBar(
                    startIcon = ImageProvider(R.drawable.baseline_bookmark_24),
                    title = "Mis bookmarks",
                    textColor = GlanceTheme.colors.onSurface,
                )
            }
        ) {
          Column(modifier = GlanceModifier.padding(5.dp)) {
              CardRow(bookmark = BookmarkDTO(
                  id = "1",
                  name = "The Cozy Café",
                  description = "A small café with a warm atmosphere.",
                  longDescription = "The Cozy Café offers a variety of delicious pastries and coffee blends. It's a perfect spot for both casual meetings and quiet afternoons.",
                  address = "123 Coffee Lane, Brewtown, CA",
                  rating = 4.5,
                  images = listOf(
                      "https://example.com/images/cozy_cafe_1.jpg",
                      "https://example.com/images/cozy_cafe_2.jpg"
                  ),
                  coordinates = Coordinates(x = 34.0522, y = -118.2437),
                  type = "Café"
              )
              )
              Spacer(modifier = GlanceModifier.height(5.dp))
          }
        }
    }
}

@Composable
private fun CardRow(modifier: GlanceModifier = GlanceModifier, bookmark: BookmarkDTO){
    val arrowDirection = R.drawable.baseline_arrow_back_24
    val kms = (Math.random()*200).roundToInt()
    Row {
        FilledButton(
            text = kms.toString() + " km " + bookmark.name,
            onClick = {},
            icon = ImageProvider(arrowDirection),
            modifier = modifier.padding(5.dp).fillMaxWidth(),
        )
    }
}
