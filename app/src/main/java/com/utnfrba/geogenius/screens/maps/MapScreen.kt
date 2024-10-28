package com.utnfrba.geogenius.screens.maps

import android.content.Context
import android.os.Bundle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.utnfrba.geogenius.screens.settings.SettingsButton

@Composable
fun MapScreen() {
    val context = LocalContext.current
    val mapView = rememberMapViewWithLifecycle(context)
    BoxWithConstraints() {
        val maxHeight = this.maxHeight
        val topHeight: Dp = maxHeight * 1 / 10

        AndroidView({ mapView }) {
            mapView.getMapAsync { googleMap ->
                val obelisc = LatLng(-34.6034743, -58.3833134)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(obelisc, 12f))
            }
        }

        Column(modifier = Modifier.align(Alignment.TopEnd)) {
            SettingsButton(modifier = Modifier
                .height(topHeight)
                .padding(15.dp)
            )
        }
    }

}

@Composable
fun rememberMapViewWithLifecycle(context: Context): MapView {
    val mapView = MapView(context)
    DisposableEffect(mapView) {
        mapView.onCreate(Bundle())
        mapView.onStart()
        mapView.onResume()

        onDispose {
            mapView.onPause()
            mapView.onStop()
            mapView.onDestroy()
        }
    }
    return mapView
}
