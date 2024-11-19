package com.utnfrba.geogenius.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Bookmark::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDAO
}

object DB {

    private lateinit var db: AppDatabase

    fun buildDB(context: Context){
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "geogenius-db"
        ).build()
    }

    fun getDB(): BookmarkDAO{
        return db.bookmarkDao()
    }
}