package com.utnfrba.geogenius.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookmarkDAO {
    @Query("SELECT * FROM bookmarks")
    fun getAll(): List<Bookmark>

    @Insert
    fun add(b: Bookmark)

    @Delete
    fun delete(b: Bookmark)
}