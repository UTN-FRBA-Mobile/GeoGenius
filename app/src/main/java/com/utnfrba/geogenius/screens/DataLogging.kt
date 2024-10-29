package com.utnfrba.geogenius.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utnfrba.geogenius.model.BookmarkDTO
import com.utnfrba.geogenius.network.BookmarkApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface BookmarkUiState {
    data class Success(val photos: List<BookmarkDTO>) : BookmarkUiState
    data object Error : BookmarkUiState
    data object Loading : BookmarkUiState
}

class BookmarkViewModel : ViewModel() {
    var bookmarkUiState: BookmarkUiState by mutableStateOf(BookmarkUiState.Loading)
        private set

    init {
        getBookmarks()
    }

    fun getBookmarks() {
        viewModelScope.launch {
            bookmarkUiState = BookmarkUiState.Loading
            bookmarkUiState = try {
                val listResult = BookmarkApi.retrofitService.getBookmarks()
                BookmarkUiState.Success(listResult)
            } catch (e: IOException) {
                BookmarkUiState.Error
            } catch (e: HttpException) {
                BookmarkUiState.Error
            }
        }
    }
}
