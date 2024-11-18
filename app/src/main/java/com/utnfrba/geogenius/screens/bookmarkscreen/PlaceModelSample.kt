package com.utnfrba.geogenius.screens.bookmarkscreen

import com.utnfrba.geogenius.model.BookmarkDTO
import com.utnfrba.geogenius.model.Coordinate

val samplePlace = BookmarkDTO(
    id = "1",
    name = "Casa Simpsons",
    address = "Avenida Siempreviva 123",
    rating = 4.5,
    description = "Una casa icónica de la serie animada Los Simpsons.",
    longDescription = "Una casa icónica de la serie animada Los Simpsons.",
    images = listOf(
        "https://via.placeholder.com/400x200/FF5733/FFFFFF?text=Imagen+1",
        "https://via.placeholder.com/400x200/33FF57/FFFFFF?text=Imagen+2",
        "https://via.placeholder.com/400x200/3357FF/FFFFFF?text=Imagen+3"
    ),
    coordinates = Coordinate(longitude = 10.0, latitude = 20.0),
    type = "Casa",
)
val samplePlace2 = BookmarkDTO(
    id = "2",
    name = "Casa Lol",
    address = "Avenida Sas 123",
    rating = 4.5,
    description = "Una casa para nada icónica",
    longDescription = "Una casa icónica de la serie animada Los Simpsons.",
    images = listOf(
        "https://via.placeholder.com/400x200/FF5733/FFFFFF?text=Imagen+1",
        "https://via.placeholder.com/400x200/33FF57/FFFFFF?text=Imagen+2",
        "https://via.placeholder.com/400x200/3357FF/FFFFFF?text=Imagen+3"
    ),
    coordinates = Coordinate(longitude = 10.0, latitude = 20.0),
    type = "Casa",
)
val sampleList = listOf(samplePlace, samplePlace2)
