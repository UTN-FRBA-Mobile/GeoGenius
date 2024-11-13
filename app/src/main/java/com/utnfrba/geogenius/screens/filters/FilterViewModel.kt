package com.utnfrba.geogenius.screens.filters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


private const val USER_PREFERENCES_NAME = "filter_preferences"

class FilterViewModel(private val dataStore: FilterDataStore): ViewModel() {
    // save state in preferences

    private val filterPreferencesFlow = dataStore.cafeCheckedFlow

    val initialSetupEvent = liveData {
        emit(dataStore.fetchInitialPreferences())
    }

    private val _cafeChecked = MutableStateFlow(false) // TODO init here with pref val
    val cafeState: StateFlow<Boolean> = _cafeChecked.asStateFlow()

    private val _museumChecked = MutableStateFlow(false)
    val museumState: StateFlow<Boolean> = _museumChecked.asStateFlow()

    private val _parkChecked = MutableStateFlow(false)
    val parkState: StateFlow<Boolean> = _parkChecked.asStateFlow()

    fun setCafeStatus(newValue: Boolean) {
        viewModelScope.launch {
            dataStore.updateCafeChecked(newValue)
        }
        _cafeChecked.update {
            newValue
        }
    }

    fun setMuseumStatus(newValue: Boolean) {
        _museumChecked.update {
            newValue
        }
    }

    fun setParkStatus(newValue: Boolean) {
        _parkChecked.update {
            newValue
        }
    }
}

class FilterViewModelFactory(
    private val repository: FilterDataStore,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FilterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}