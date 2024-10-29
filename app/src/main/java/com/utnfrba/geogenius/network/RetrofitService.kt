package com.utnfrba.geogenius.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.utnfrba.geogenius.model.BookmarkDTO
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL =
    "https://run.mocky.io"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .build()

interface BookmarkApiService {
    @GET("v3/e8d59cea-ab3d-4c3e-adbd-7ca61e0dc9b9")
    suspend fun getBookmarks(): List<BookmarkDTO>
}

object BookmarkApi {
    val retrofitService : BookmarkApiService by lazy {
        retrofit.create(BookmarkApiService::class.java)
    }
}
