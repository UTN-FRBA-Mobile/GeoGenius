package com.utnfrba.geogenius.screens.bookmarkscreen

data class PlaceModel(
    val id: String,
    val images: List<String>,
    val name: String,
    val address: String,
    val rating: Float,
    val description: String,
)