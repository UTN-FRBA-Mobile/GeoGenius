package com.utnfrba.geogenius.screens.filters

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

object PreferencesKeys {
    val CAFE_CHECKED = booleanPreferencesKey("cafe_checked")
    val MUSEUM_CHECKED = booleanPreferencesKey("museum_checked")
    val PARK_CHECKED = booleanPreferencesKey("park_checked")
    val WIDGET_COUNT = intPreferencesKey("widget_count")
}

class FilterDataStore(
    private val dataStore: DataStore<Preferences>
) {

    val stateFlow: Flow<FilterState> = getProps()

    suspend fun saveFilterChanged(newValue: Boolean, filter: Preferences.Key<Boolean>) {
        dataStore.edit { preferences ->
            preferences[filter] = newValue
        }
    }

    suspend fun saveWidgetCountChanged(newValue: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.WIDGET_COUNT] = newValue
        }
    }

    private fun getProps(): Flow<FilterState>{
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val cafeChecked = preferences[PreferencesKeys.CAFE_CHECKED] ?: false
            val museumChecked = preferences[PreferencesKeys.MUSEUM_CHECKED] ?: false
            val parkChecked = preferences[PreferencesKeys.PARK_CHECKED] ?: false
            val widgetCount = preferences[PreferencesKeys.WIDGET_COUNT] ?: 1
            FilterState(cafeChecked,museumChecked,parkChecked,widgetCount)
        }
    }
    // https://github.com/android/codelab-android-datastore/blob/preferences_datastore/app/src/main/java/com/codelab/android/datastore/data/UserPreferencesRepository.kt
    // https://medium.com/@rowaido.game/persistent-data-storage-using-datastore-preferences-in-jetpack-compose-90c481bfed12
}




