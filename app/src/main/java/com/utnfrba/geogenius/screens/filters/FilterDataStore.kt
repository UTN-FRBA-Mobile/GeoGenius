package com.utnfrba.geogenius.screens.filters

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map


class FilterDataStore(
    private val dataStore: DataStore<Preferences>
) {
    private companion object PreferencesKeys {
        val CAFE_CHECKED = booleanPreferencesKey("cafe_checked")
        val MUSEUM_CHECKED = booleanPreferencesKey("museum_checked")
        val PARK_CHECKED = booleanPreferencesKey("park_checked")
    }
    val cafeCheckedFlow: Flow<Boolean> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        val cafeChecked = preferences[CAFE_CHECKED] ?: false
        cafeChecked
    }

    suspend fun saveCafeChecked(newValue: Boolean) {
        dataStore.edit { preferences ->
            preferences[CAFE_CHECKED] = newValue
        }
    }
    // https://github.com/android/codelab-android-datastore/blob/preferences_datastore/app/src/main/java/com/codelab/android/datastore/data/UserPreferencesRepository.kt
    // https://medium.com/@rowaido.game/persistent-data-storage-using-datastore-preferences-in-jetpack-compose-90c481bfed12
}




