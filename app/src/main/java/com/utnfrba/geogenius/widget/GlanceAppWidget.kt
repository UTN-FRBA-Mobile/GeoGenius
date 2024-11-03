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
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import com.utnfrba.geogenius.R
import com.utnfrba.geogenius.model.BookmarkDTO
import com.utnfrba.geogenius.model.Coordinate
import kotlin.math.roundToInt

class GlanceAppWidget : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = GeoGeniusWidget()
}

class GeoGeniusWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {

        // In this method, load data needed to render the AppWidget.
        // Use `withContext` to switch to another thread for long running
        // operations.

        provideContent {
            Content(
                arrayOf(
                    BookmarkDTO(
                        id = "1",
                        name = "Cafe lol",
                        description = "Gran cafe",
                        longDescription = "Buen lugar para personas fanaticas del cafe con una larga historia",
                        address = "Foo 123",
                        rating = 4.3,
                        images = listOf(),
                        coordinates = Coordinate(-34.0923, -53.43556),
                        type = "cafe",
                    ),
                    BookmarkDTO(
                        id = "1",
                        name = "Cafe lol",
                        description = "Gran cafe",
                        longDescription = "Buen lugar para personas fanaticas del cafe con una larga historia",
                        address = "Foo 123",
                        rating = 4.3,
                        images = listOf(),
                        coordinates = Coordinate(-34.0923, -53.43556),
                        type = "cafe",
                    )
                )
            )
        }
    }

    @Composable
    private fun Content(bookmarks: Array<BookmarkDTO>, modifier: GlanceModifier = GlanceModifier) {
        Scaffold(
            modifier = modifier,
            backgroundColor = GlanceTheme.colors.widgetBackground,
            titleBar = {
                TitleBar(
                    startIcon = ImageProvider(R.drawable.baseline_bookmark_24),
                    title = "Mis bookmarks", // TODO add this to locals
                    textColor = GlanceTheme.colors.onSurface,
                )
            }
        ) {
            Column(modifier = GlanceModifier.padding(5.dp)) {
                bookmarks.forEach { b ->
                    CardRow(b)
                    Spacer(modifier = GlanceModifier.padding(5.dp))
                }
            }
        }
    }
}

@Composable
private fun CardRow(bookmark: BookmarkDTO, modifier: GlanceModifier = GlanceModifier) {
    val kms = (Math.random() * 200).roundToInt()
    val currentDirection = Coordinate(x = -34.0, y = -53.0) // TODO get from phone
    val arrowDirection = getDirectionToReach(bookmark.coordinates, currentDirection).icon
    FilledButton(
        text = kms.toString() + " km " + bookmark.name,
        onClick = {},
        icon = ImageProvider(arrowDirection),
        modifier = modifier.padding(5.dp).fillMaxWidth(),
    )
}
