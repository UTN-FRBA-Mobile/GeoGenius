package com.utnfrba.geogenius.screens.filters

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "setting"
)
class FilterApplication: Application() {
    lateinit var filterRepository: FilterDataStore
    override fun onCreate() {
        super.onCreate()
        filterRepository = FilterDataStore(dataStore)
    }
}