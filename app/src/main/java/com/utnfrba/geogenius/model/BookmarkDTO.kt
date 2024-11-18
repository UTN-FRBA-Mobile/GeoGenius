package com.utnfrba.geogenius.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookmarkDTO(
    val id: String,
    val name: String,
    val description: String,
    @SerialName("long_description")
    val longDescription: String,
    val address: String,
    val rating: Double,
    val images: List<String>,
    val coordinates: Coordinate,
    val type: String
)

@Serializable
data class Coordinate(
    /**
     * Longitude
     */
    @SerialName("x")
    val longitude: Double,

    /**
     * Latitude
     */
    @SerialName("y")
    val latitude: Double
)
