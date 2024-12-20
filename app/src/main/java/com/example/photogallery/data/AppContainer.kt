package com.example.photogallery.data

import com.example.photogallery.network.AnimeApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val animePhotosRepository: AnimePhotosRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://api.hn/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: AnimeApiService by lazy {
        retrofit.create(AnimeApiService::class.java)
    }

    override val animePhotosRepository: AnimePhotosRepository by lazy {
        NetworkAnimePhotosRepository(retrofitService)
    }

}