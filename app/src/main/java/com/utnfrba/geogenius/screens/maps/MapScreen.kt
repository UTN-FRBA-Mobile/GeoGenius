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
import androidx.compose.runtime.collectAsState
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
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.utnfrba.geogenius.appnavigation.Screen
import com.utnfrba.geogenius.model.BookmarkDTO
import com.utnfrba.geogenius.screens.bookmarkscreen.BookmarkViewModel
import com.utnfrba.geogenius.screens.bookmarkscreen.LoadingBookmarkComposable
import com.utnfrba.geogenius.screens.filters.FilterState
import com.utnfrba.geogenius.screens.filters.FilterViewModel
import com.utnfrba.geogenius.screens.filters.FiltersRepository

@Composable
fun MapScreen(
    navController: NavController,
    bookmarkViewModel: BookmarkViewModel,
    filterViewModel: FilterViewModel
) {
    val filters = filterViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val mapView = rememberMapViewWithLifecycle(context)
    lateinit var gMap: GoogleMap
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
    LoadingBookmarkComposable(bookmarkViewModel, saved = false) { b ->
        val bookmarks = b.value.filter { FiltersRepository.isEnabled(it.type) }
        var markers: List<Marker?> = listOf()
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
                            Log.e(
                                "MapScreen",
                                "Error al intentar obtener la ubicación: ${it.message}"
                            )
                        }
                    }
                    markers = createMarkersFromBookmarks(
                        googleMap, bookmarks,
                    )
                    googleMap.setOnMarkerClickListener { marker ->
                        val markerLocation = marker.position
                        val bookmark = bookmarks.find { b ->
                            b.coordinates.latitude == markerLocation.latitude && b.coordinates.longitude == markerLocation.longitude
                        }
                        if (bookmark != null) {
                            navController.navigate(Screen.BookmarkDetail.route + "/${bookmark.id}")
                        }
                        true
                    }
                }
            }

            SearchBarComponent(
                bookmarks,
                { id: String ->
                    val bookmark = bookmarks.find { b -> b.id == id }
                    val bookmarkIndex = bookmarks.indexOf(bookmark)
                    if (bookmark != null) {
                        gMap.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    bookmark.coordinates.latitude,
                                    bookmark.coordinates.longitude
                                ),
                                15f
                            )
                        )
                        markers[bookmarkIndex]?.showInfoWindow()
                    }
                })
        }
    }
}

private fun createMarkersFromBookmarks(
    googleMap: GoogleMap,
    bookmarks: List<BookmarkDTO>
): List<Marker?> {
    return bookmarks.map { bookmark ->
        val position = LatLng(bookmark.coordinates.latitude, bookmark.coordinates.longitude)
        val markerOptions = MarkerOptions()
            .position(position)
            .title(bookmark.name)
        googleMap.addMarker(markerOptions)
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
