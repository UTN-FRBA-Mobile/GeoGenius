package com.utnfrba.geogenius.screens.filters

import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


data class FilterState(
    val cafeChecked: Boolean,
    val museumChecked: Boolean,
    val parkChecked: Boolean,
    val widgetCount: Int
)

class FilterViewModel(private val dataStore: FilterDataStore) : ViewModel() {
    val uiState: StateFlow<FilterState> =
        dataStore.stateFlow.map { checked ->
            FilterState(
                cafeChecked = checked.cafeChecked,
                museumChecked = checked.museumChecked,
                parkChecked = checked.parkChecked,
                widgetCount = checked.widgetCount
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FilterState(
                cafeChecked = false,
                museumChecked = false,
                parkChecked = false,
                widgetCount = 1
            )
        )


    fun saveFilterValue(value: Boolean, filter: Preferences.Key<Boolean>) {
        viewModelScope.launch {
            dataStore.saveFilterChanged(value, filter)
        }
    }

    fun saveWidgetCount(value: Int) {
        viewModelScope.launch {
            dataStore.saveWidgetCountChanged(value)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FilterApplication)
                FilterViewModel(application.filterRepository)
            }
        }
    }
}
