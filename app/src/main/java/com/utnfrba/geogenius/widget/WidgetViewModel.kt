package com.utnfrba.geogenius.widget

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import androidx.glance.GlanceId
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utnfrba.geogenius.model.Coordinate
import kotlinx.coroutines.launch

object WidgetViewModel : ViewModel() {
    private var cachedLocation: Coordinate = Coordinate(0.0, 0.0)
    fun updateWidget(context: Context, id: GlanceId) {
        this.updateLocation(context, id)
        viewModelScope.launch {
            GeoGeniusWidget().update(context, id)
        }
    }

    fun getCachedLocation(): Coordinate {
        return cachedLocation
    }

    @SuppressLint("MissingPermission")
    fun updateLocation(context: Context, id: GlanceId) {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers: List<String> = locationManager.getProviders(true)
        var location: Location? = null
        for (i in providers.size - 1 downTo 0) {
            location = locationManager.getLastKnownLocation(providers[i])
            if (location != null)
                break
        }
        if (location != null) {
            cachedLocation =
                Coordinate(latitude = location.latitude, longitude = location.longitude)
        }
    }
}