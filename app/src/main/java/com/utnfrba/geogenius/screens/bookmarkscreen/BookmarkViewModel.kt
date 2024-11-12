package com.utnfrba.geogenius.screens.bookmarkscreen

import BookmarkRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utnfrba.geogenius.model.BookmarkDTO
import kotlinx.coroutines.launch

class BookmarkViewModel : ViewModel() {
    var bookmarks: List<BookmarkDTO> = emptyList()
        private set

    var errorMessage: String? = null
        private set

    var isLoading: Boolean = true
        private set

    init {
        viewModelScope.launch {
            try {
                isLoading = true
                val result = BookmarkRepository().getBookmarks()
                if (result.isSuccess) {
                    bookmarks = result.getOrNull() ?: emptyList()
                } else {
                    errorMessage = "Error loading bookmarks: ${result.exceptionOrNull()?.localizedMessage}"
                }
            } catch (e: Exception) {
                errorMessage = "Unexpected error: ${e.localizedMessage}"
            } finally {
                isLoading = false
            }
        }
    }
}