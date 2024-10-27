package com.utnfrba.geogenius.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utnfrba.geogenius.network.MarsApi
import kotlinx.coroutines.launch

class ExampleModel: ViewModel() {
    fun getMarsPhotos() {
        viewModelScope.launch {
            val list = MarsApi.retrofitService.getPhotos()
            Log.d("Lista", list)
        }
    }
}
