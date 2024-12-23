package com.example.photogallery.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL =
    "https://api.hn/"

private val retrofit = Retrofit
    .Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()
// 添加转换器


interface AnimeApiService {
    @OptIn(InternalSerializationApi::class)
    @GET("name.php?return=jsonpro")
    suspend fun getPhotos(): List<AnimePhoto>

}

