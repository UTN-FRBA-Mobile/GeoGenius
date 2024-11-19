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

    private lateinit var instance: AppDatabase

    fun buildDB(context: Context){
        instance = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "geogenius-db"
        ).allowMainThreadQueries().build()
    }

    fun getInstance(): AppDatabase {
        return instance
    }
}
