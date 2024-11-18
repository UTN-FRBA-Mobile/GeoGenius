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

// Uses http://en.wikipedia.org/wiki/Haversine_formula
fun distanceInKmBetweenEarthCoordinates(c1: Coordinate, c2: Coordinate): Double {
    val earthRadiusKm = 6371.0

    val dLat = degreesToRadians(c2.x - c1.x)
    val dLon = degreesToRadians(c2.y - c2.y)

    val lat1Rad = degreesToRadians(c1.x)
    val lat2Rad = degreesToRadians(c2.x)

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
    val xDifference = destination.x - origin.x
    val yDifference = destination.y - origin.y

    return if (xDifference.absoluteValue > yDifference.absoluteValue) {
        if (yDifference > 0) ArrowDirection.UP else ArrowDirection.DOWN
    } else {
        if (xDifference > 0) ArrowDirection.RIGHT else ArrowDirection.LEFT
    }
}
