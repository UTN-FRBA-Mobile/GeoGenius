package com.utnfrba.geogenius.data

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

private const val BASE_URL =
    "https://android-kotlin-fun-mars-server.appspot.com"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(ScalarsConverterFactory.create())
    .build()

interface MarsApiService {
    @GET("photos")
    suspend fun getPhotos(): String
}

object MarsApi {
    val retrofitService : MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}
