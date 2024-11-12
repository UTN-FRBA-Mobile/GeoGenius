package com.utnfrba.geogenius.screens.filters

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

private const val USER_PREFERENCES_NAME = "filter_preferences"

private val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)

private object PreferencesKeys {
    val CAFE_CHECKED = booleanPreferencesKey("cafe_checked")
    val MUSEUM_CHECKED = booleanPreferencesKey("museum_checked")
    val PARK_CHECKED = booleanPreferencesKey("park_checked")
}

data class FilterPreferences(val cafeChecked: Boolean)

class FilterDataStore(
    private val dataStore: DataStore<Preferences>,
    context: Context
) {
    val userPreferencesFlow: Flow<FilterPreferences> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        val cafeChecked = preferences[PreferencesKeys.CAFE_CHECKED] ?: false
        FilterPreferences(cafeChecked)
    }

    suspend fun updateCafeChecked(newValue: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.CAFE_CHECKED] = newValue
        }
    }
}




