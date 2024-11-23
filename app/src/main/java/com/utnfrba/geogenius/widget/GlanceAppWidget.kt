package com.utnfrba.geogenius.widget

import BookmarkRepository
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.components.FilledButton
import androidx.glance.appwidget.components.OutlineButton
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.components.TitleBar
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.utnfrba.geogenius.MainActivity
import com.utnfrba.geogenius.R
import com.utnfrba.geogenius.appnavigation.Screen
import com.utnfrba.geogenius.model.BookmarkDTO
import com.utnfrba.geogenius.model.Coordinate
import com.utnfrba.geogenius.screens.filters.DATASTORE_NAME
import com.utnfrba.geogenius.screens.filters.PreferencesKeys
import com.utnfrba.geogenius.screens.filters.dataStore
import java.io.File
import kotlin.math.min

class GlanceAppWidget : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = GeoGeniusWidget()
}

class GeoGeniusWidget : GlanceAppWidget() {
    override val stateDefinition: GlanceStateDefinition<*>
        get() = CustomGlanceStateDefinition

    override suspend fun provideGlance(context: Context, id: GlanceId) {

        provideContent {
            val prefs = currentState<Preferences>()
            val widgetCount = remember { prefs[PreferencesKeys.WIDGET_COUNT] ?: 1 }
            WidgetViewModel.updateLocation(context)
            val currentDirection = WidgetViewModel.getCachedLocation()
            Content(
                getSortedBookmarks(currentDirection),
                widgetCount,
                { WidgetViewModel.updateWidget(context, id) },
                currentDirection
            )
        }
    }

    private fun getSortedBookmarks(currentDirection: Coordinate): List<BookmarkDTO> {
        val list: List<BookmarkDTO> = BookmarkRepository.getCachedBookmarks() ?: listOf()
        return list.sortedBy { b ->
            distanceInKmBetweenEarthCoordinates(
                b.coordinates,
                currentDirection
            )
        }
    }

    @Composable
    fun Content(
        bookmarks: List<BookmarkDTO>,
        widgetCount: Int,
        onClick: () -> Unit,
        currentDirection: Coordinate,
        modifier: GlanceModifier = GlanceModifier
    ) {
        Scaffold(
            modifier = modifier,
            backgroundColor = GlanceTheme.colors.widgetBackground,
            titleBar = {
                Row {
                    OutlineButton(
                        text = "",
                        contentColor = ColorProvider(Color.Transparent),
                        onClick = onClick,
                        icon = ImageProvider(R.drawable.baseline_refresh_24),
                        modifier = GlanceModifier.background(Color.Transparent),
                    )
                    TitleBar(
                        startIcon = ImageProvider(R.drawable.baseline_bookmark_24),
                        title = LocalContext.current.getString(R.string.widgetTitle),
                        textColor = GlanceTheme.colors.onSurface,
                    )
                }
            }
        ) {
            if (currentDirection.latitude == 0.0 && currentDirection.longitude == 0.0) {
                Text(
                    LocalContext.current.getString(R.string.locationError),
                    style = TextStyle(color = ColorProvider(Color.White))
                )
            } else {
                Column(modifier = GlanceModifier.padding(5.dp)) {
                    bookmarks.slice(0..<min(widgetCount, bookmarks.size)).forEach { b ->
                        CardRow(b, currentDirection)
                        Spacer(modifier = GlanceModifier.padding(5.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun CardRow(
    bookmark: BookmarkDTO,
    currentDirection: Coordinate,
    modifier: GlanceModifier = GlanceModifier
) {
    val kms = distanceInKmBetweenEarthCoordinates(bookmark.coordinates, currentDirection)
    val arrowDirection = getDirectionToReach(bookmark.coordinates, currentDirection).icon
    FilledButton(
        text = "${formatDistance(kms)}: ${bookmark.name}",
        onClick = actionStartActivity(
            Intent(LocalContext.current.applicationContext, MainActivity::class.java)
                .setAction(Intent.ACTION_VIEW)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .setData(("https://geogenius.utnfrba.com/" + Screen.BookmarkDetail.route + "?placeId=" + bookmark.id).toUri()),
        ),
        icon = ImageProvider(arrowDirection),
        modifier = modifier.padding(5.dp).fillMaxWidth(),
    )
}

object CustomGlanceStateDefinition : GlanceStateDefinition<Preferences> {
    override suspend fun getDataStore(context: Context, fileKey: String): DataStore<Preferences> {
        return context.dataStore
    }

    override fun getLocation(context: Context, fileKey: String): File {
        return File(context.applicationContext.filesDir, "datastore/$DATASTORE_NAME")
    }

}

fun formatDistance(kms: Double): String {
    return if (kms >= 1) {
        "${round(kms, 1)} km"
    } else {
        "${(kms * 1000).toInt()} m"
    }
}
