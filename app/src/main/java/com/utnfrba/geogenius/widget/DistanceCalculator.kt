package com.utnfrba.geogenius.widget

import com.utnfrba.geogenius.R
import com.utnfrba.geogenius.model.Coordinate
import kotlin.math.absoluteValue
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

private fun degreesToRadians(degrees: Double): Double {
    return degrees * Math.PI / 180
}

/**
 * Uses Haversine formula
 */
fun distanceInKmBetweenEarthCoordinates(c1: Coordinate, c2: Coordinate): Double {
    val earthRadiusKm = 6371.0
    val dLat = degreesToRadians(c2.longitude - c1.longitude)
    val dLon = degreesToRadians(c2.latitude - c1.latitude)

    val lat1Rad = degreesToRadians(c1.longitude)
    val lat2Rad = degreesToRadians(c2.longitude)

    val a = sin(dLat / 2) * sin(dLat / 2) +
            sin(dLon / 2) * sin(dLon / 2) * cos(lat1Rad) * cos(lat2Rad)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return earthRadiusKm * c
}

enum class ArrowDirection(val icon: Int) {
    LEFT(R.drawable.baseline_arrow_forward_24),
    RIGHT(R.drawable.baseline_arrow_back_24),
    UP(R.drawable.baseline_arrow_upward_24),
    DOWN(R.drawable.baseline_arrow_downward_24)
}

fun getDirectionToReach(destination: Coordinate, origin: Coordinate): ArrowDirection {
    val xDifference = destination.latitude - origin.latitude
    val yDifference = destination.longitude - origin.longitude

    return if (xDifference.absoluteValue > yDifference.absoluteValue) {
        if (yDifference > 0) ArrowDirection.UP else ArrowDirection.DOWN
    } else {
        if (xDifference > 0) ArrowDirection.RIGHT else ArrowDirection.LEFT
    }
}

fun round(number: Double, decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return kotlin.math.round(number * multiplier) / multiplier
}
