package com.utnfrba.geogenius.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.utnfrba.geogenius.model.BookmarkDTO
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL =
    "http://192.168.0.17:3000"
// https://run.mocky.io/v3/9302a605-a8fa-44e7-a948-2ae81b3e6657

interface BookmarkApiService {
    @GET("/")
    suspend fun getBookmarks(): List<BookmarkDTO>
}

object BookmarkApi {
    val retrofitService: BookmarkApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(BookmarkApiService::class.java)
    }
}
