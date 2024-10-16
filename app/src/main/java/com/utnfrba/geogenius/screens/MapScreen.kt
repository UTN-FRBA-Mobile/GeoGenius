package com.utnfrba.geogenius.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.utnfrba.geogenius.R

@Composable
fun MapScreen(activity: FragmentActivity) {
    AndroidView(
        factory = {
            val mapFragment = activity.supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync { googleMap ->
                val obelisc = LatLng(-34.6034743, -58.3833134)
                googleMap.addMarker(
                    MarkerOptions().position(obelisc).title("Marcador en Buenos Aires")
                )
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(obelisc, 12f))
            }
            mapFragment.requireView()
        }
    )
}
