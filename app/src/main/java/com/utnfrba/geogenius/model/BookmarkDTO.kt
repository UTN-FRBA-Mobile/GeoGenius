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
    val coordinates: Coordinates,
    val type: String
)

@Serializable
data class Coordinates(
    val x: Double,
    val y: Double
)
