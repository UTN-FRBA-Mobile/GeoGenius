package com.utnfrba.geogenius.screens.bookmarkscreen

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.utnfrba.geogenius.model.BookmarkDTO

data class BookmarkDatastoreState(
    val savedBookmarks: List<BookmarkDTO>
)


class BookmarkDataStore (private val dataStore: DataStore<Preferences>){

}
