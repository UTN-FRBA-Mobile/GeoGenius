package com.utnfrba.geogenius.screens.bookmarkscreen

import BookmarkRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utnfrba.geogenius.model.BookmarkDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookmarkViewModel : ViewModel() {
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _bookmarks = MutableStateFlow<List<BookmarkDTO>>(emptyList())
    val bookmarks: StateFlow<List<BookmarkDTO>> = _bookmarks

    init {
        viewModelScope.launch {
            _isLoading.value = true
            val result = BookmarkRepository.getBookmarks()
            if (result.isSuccess) {
                _bookmarks.value = result.getOrNull() ?: emptyList()
            } else {
                _errorMessage.value =
                    "Error loading bookmarks: ${result.exceptionOrNull()?.localizedMessage}"
            }
            _isLoading.value = false
        }
    }
}