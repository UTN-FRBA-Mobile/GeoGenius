package com.utnfrba.geogenius.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.utnfrba.geogenius.model.BookmarkDTO
import com.utnfrba.geogenius.model.Coordinate

@Entity(tableName = "bookmarks")
data class Bookmark(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,

    @ColumnInfo(name = "long_description") val longDescription: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "rating") val rating: Double,

    @ColumnInfo(name = "type") val type: String,

    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,

    // We store the first image only for practical purposes
    @ColumnInfo(name = "image") val image: String
)

fun bookmarkToDTO(b: Bookmark): BookmarkDTO {
    return BookmarkDTO(
        b.id, b.name, b.description,
        b.longDescription, b.address, b.rating,
        listOf(b.image),
        Coordinate(b.latitude, b.longitude), b.type
    )
}

fun dtoToBookmark(b: BookmarkDTO): Bookmark {
    return Bookmark(
        b.id, b.name, b.description,
        b.longDescription, b.address, b.rating,
        b.type, b.coordinates.latitude, b.coordinates.longitude,
        b.images[0]
    )
}
