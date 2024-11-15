package com.utnfrba.geogenius.screens.bookmarkscreen

import BookmarkRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utnfrba.geogenius.model.BookmarkDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookmarkDetailViewModel : ViewModel() {
    var errorMessage: String? = null
    var isLoading: Boolean = true
    private val _bookmark = MutableStateFlow<BookmarkDTO?>(null)
    val bookmark: StateFlow<BookmarkDTO?> = _bookmark

    fun loadBookmark(id: String?) {
        viewModelScope.launch {
            isLoading = true
            id?.let {
                val result = BookmarkRepository.getBookmarksById(it)
                if (result.isSuccess) {
                    _bookmark.value = result.getOrNull()
                } else {
                    errorMessage =
                        "Error loading bookmark: ${result.exceptionOrNull()?.localizedMessage}"
                }
                isLoading = false
            }
        }
    }
}