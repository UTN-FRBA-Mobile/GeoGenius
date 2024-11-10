package com.utnfrba.geogenius.widget

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WidgetViewModel: ViewModel() {
    private var bookmarkCountState: Int by mutableIntStateOf(1)

    fun getBookmarkCount(): Int {
        return bookmarkCountState
    }

    fun setBookmarkCount(newCount: Int) {
        bookmarkCountState = newCount
    }
}