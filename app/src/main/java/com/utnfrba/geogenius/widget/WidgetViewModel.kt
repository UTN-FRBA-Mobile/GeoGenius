package com.utnfrba.geogenius.widget

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WidgetViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(1)
    val uiState: StateFlow<Int> = _uiState.asStateFlow()

    fun setBookmarkCount(newCount: Int) {
        _uiState.update {
            newCount
        }
    }
}