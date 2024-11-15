package com.utnfrba.geogenius.screens.maps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.google.android.gms.maps.model.MarkerOptions
import com.utnfrba.geogenius.model.BookmarkDTO
import com.utnfrba.geogenius.model.Coordinate

@Composable
fun MapScreen(initialBookmarks: List<BookmarkDTO>) {
    val bookmarks = remember { initialBookmarks.toMutableList() }

    addTestBookmarks(bookmarks)

    val context = LocalContext.current
    val mapView = rememberMapViewWithLifecycle(context)
    val fusedLocationClient: FusedLocationProviderClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    var hasLocationPermission by remember {
        mutableStateOf(
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
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
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
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
                            googleMap.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    userLocation,
                                    15f
                                )
                            )
                            googleMap.isMyLocationEnabled = true
                        } else {
                            Log.e(
                                "MapScreen",
                                "No se pudo obtener la ubicación actual. Asegurate de que la ubicación está activada en el dispositivo."
                            )
                        }
                    }.addOnFailureListener {
                        Log.e("MapScreen", "Error al intentar obtener la ubicación: ${it.message}")
                    }
                }

                addBookmarksToMap(googleMap, bookmarks)
            }
        }

        SearchBarComponent()
    }
}


private fun addBookmarksToMap(googleMap: GoogleMap, bookmarks: List<BookmarkDTO>) {
    for (bookmark in bookmarks) {
        val position = LatLng(bookmark.coordinates.x, bookmark.coordinates.y)
        val markerOptions = MarkerOptions()
            .position(position)
            .title(bookmark.name)
        googleMap.addMarker(markerOptions)
    }
}

private fun addTestBookmarks(bookmarks: MutableList<BookmarkDTO>) {
    // Agregar un bookmark en 37°25'25.9"N 122°05'24.5"W
    val bookmark1 = BookmarkDTO(
        id = "1",
        name = "Ubicación de Prueba 1",
        description = "Descripción para ubicación de prueba 1",
        longDescription = "Esta es una descripción larga de la ubicación de prueba 1",
        address = "Dirección de prueba 1",
        rating = 4.5,
        images = listOf(),
        coordinates = Coordinate(x = 37.423861, y = -122.090139),
        type = "Test"
    )

    // Agregar un bookmark en 37°25'14.1"N 122°04'41.0"W
    val bookmark2 = BookmarkDTO(
        id = "2",
        name = "Ubicación de Prueba 2",
        description = "Descripción para ubicación de prueba 2",
        longDescription = "Esta es una descripción larga de la ubicación de prueba 2",
        address = "Dirección de prueba 2",
        rating = 4.8,
        images = listOf(),
        coordinates = Coordinate(x = 37.420583, y = -122.078056),
        type = "Test"
    )

    // Añadir los bookmarks a la lista
    bookmarks.add(bookmark1)
    bookmarks.add(bookmark2)
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
