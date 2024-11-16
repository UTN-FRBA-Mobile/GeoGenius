package com.utnfrba.geogenius.screens.maps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.utnfrba.geogenius.appnavigation.Screen
import com.utnfrba.geogenius.model.BookmarkDTO
import com.utnfrba.geogenius.model.Coordinate

@Composable
fun MapScreen(navController: NavController) {
    val bookmarks = addTestBookmarks()

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
    lateinit var gMap: GoogleMap
    Box {
        AndroidView({ mapView }) {
            mapView.getMapAsync { googleMap: GoogleMap ->
                gMap = googleMap
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

                addBookmarksToMap(googleMap, bookmarks
                ) { marker ->
                    marker.showInfoWindow()
                    val markerLocation = marker.position
                    val bookmark = bookmarks.find { b ->
                        b.coordinates.x == markerLocation.latitude && b.coordinates.y == markerLocation.longitude
                    }
                    if (bookmark != null) {
                        navController.navigate(Screen.BookmarkDetail.route + "/${bookmark.id}")
                    }
                    true
                }
            }
        }
        // old: { id: String -> navController.navigate(Screen.BookmarkDetail.route + "/${id}") }
        SearchBarComponent(
            bookmarks,
            { id: String ->
                val bookmark = bookmarks.find { b -> b.id == id }
                if (bookmark != null) {
                    gMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(bookmark.coordinates.x, bookmark.coordinates.y),
                            15f
                        )
                    )
                }
            })
    }
}


private fun addBookmarksToMap(googleMap: GoogleMap, bookmarks: List<BookmarkDTO>, onMarkerClick: GoogleMap.OnMarkerClickListener) {
    for (bookmark in bookmarks) {
        val position = LatLng(bookmark.coordinates.x, bookmark.coordinates.y)
        val markerOptions = MarkerOptions()
            .position(position)
            .title(bookmark.name)
        googleMap.addMarker(markerOptions)
        googleMap.setOnMarkerClickListener(onMarkerClick)
    }
}

private fun addTestBookmarks(): List<BookmarkDTO> {
    // TODO get from repo
    val bookmark1 = BookmarkDTO(
        id = "1",
        name = "Cabildo",
        description = "Descripción para ubicación de prueba 1",
        longDescription = "Esta es una descripción larga de la ubicación de prueba 1",
        address = "Dirección de prueba 1",
        rating = 4.5,
        images = listOf(),
        coordinates = Coordinate(x = 37.423861, y = -122.090139),
        type = "Test"
    )

    val bookmark2 = BookmarkDTO(
        id = "2",
        name = "Las violetas",
        description = "Descripción para ubicación de prueba 2",
        longDescription = "Esta es una descripción larga de la ubicación de prueba 2",
        address = "Dirección de prueba 2",
        rating = 4.8,
        images = listOf(),
        coordinates = Coordinate(x = 37.420583, y = -122.078056),
        type = "Test"
    )

    return listOf(bookmark1, bookmark2)
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
