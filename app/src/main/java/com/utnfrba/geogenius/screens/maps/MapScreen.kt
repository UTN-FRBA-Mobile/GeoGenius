package com.utnfrba.geogenius.screens.maps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng

@Composable
fun MapScreen() {
    val context = LocalContext.current
    val mapView = rememberMapViewWithLifecycle(context)
    val fusedLocationClient: FusedLocationProviderClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    var hasLocationPermission by remember {
        mutableStateOf(
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        )
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasLocationPermission = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true &&
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
    }

    if (!hasLocationPermission) {
        LaunchedEffect(Unit) {
            locationPermissionLauncher.launch(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            )
        }
    }

    BoxWithConstraints {
        val maxHeight = this.maxHeight
        val topHeight: Dp = maxHeight * 1 / 10

        AndroidView({ mapView }) {
            mapView.getMapAsync { googleMap: GoogleMap ->
                if (hasLocationPermission) {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        if (location != null) {
                            val userLocation = LatLng(location.latitude, location.longitude)
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
                            googleMap.isMyLocationEnabled = true
                        } else {
                            Log.e("MapScreen", "No se pudo obtener la ubicación actual. Asegurate de que la ubicación está activada en el dispositivo.")
                        }
                    }.addOnFailureListener {
                        Log.e("MapScreen", "Error al intentar obtener la ubicación: ${it.message}")
                    }
                }
            }
        }

        Column {
            SearchBar(
                modifier = Modifier
                    .height(topHeight)
            )
        }
    }
}

@Composable
fun rememberMapViewWithLifecycle(context: Context): MapView {
    val mapView = remember { MapView(context) }
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
