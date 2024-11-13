package com.utnfrba.geogenius.screens.filters

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
    val cafeChecked: Boolean
)

class FilterViewModel(private val dataStore: FilterDataStore) : ViewModel() {
    val uiState: StateFlow<FilterState> =
        dataStore.cafeCheckedFlow.map { checked ->
            FilterState(checked)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FilterState(false)
        )

    fun saveCafeFilter(value: Boolean) {
        viewModelScope.launch {
            dataStore.saveCafeChecked(value)
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
