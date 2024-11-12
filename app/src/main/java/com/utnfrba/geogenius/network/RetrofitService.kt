package com.utnfrba.geogenius.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.utnfrba.geogenius.model.BookmarkDTO
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL =
    "https://run.mocky.io"

interface BookmarkApiService {
    @GET("/v3/2daeddf1-158c-4e88-b17b-4cee39702e47")
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
