package com.utnfrba.geogenius.screens.filters

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FilterViewModel {
    private val _cafeChecked = MutableStateFlow(false)
    val cafeState: StateFlow<Boolean> = _cafeChecked.asStateFlow()

    fun setCafeStatus(newValue: Boolean) {
        _cafeChecked.update {
            newValue
        }
    }
}