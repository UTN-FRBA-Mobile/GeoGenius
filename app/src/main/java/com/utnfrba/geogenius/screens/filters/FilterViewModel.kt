package com.utnfrba.geogenius.screens.filters

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FilterViewModel: ViewModel() {
    // save state in preferences
    private val _cafeChecked = MutableStateFlow(false)
    val cafeState: StateFlow<Boolean> = _cafeChecked.asStateFlow()

    private val _museumChecked = MutableStateFlow(false)
    val museumState: StateFlow<Boolean> = _museumChecked.asStateFlow()

    private val _parkChecked = MutableStateFlow(false)
    val parkState: StateFlow<Boolean> = _parkChecked.asStateFlow()

    fun setCafeStatus(newValue: Boolean) {
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