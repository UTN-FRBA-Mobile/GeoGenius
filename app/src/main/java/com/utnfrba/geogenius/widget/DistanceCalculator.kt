package com.utnfrba.geogenius.widget

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

private fun degreesToRadians(degrees: Double): Double {
    return degrees * Math.PI / 180
}

fun distanceInKmBetweenEarthCoordinates(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val earthRadiusKm = 6371.0

    val dLat = degreesToRadians(lat2 - lat1)
    val dLon = degreesToRadians(lon2 - lon1)

    val lat1Rad = degreesToRadians(lat1)
    val lat2Rad = degreesToRadians(lat2)

    val a = sin(dLat / 2) * sin(dLat / 2) +
            sin(dLon / 2) * sin(dLon / 2) * cos(lat1Rad) * cos(lat2Rad)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return earthRadiusKm * c
}
