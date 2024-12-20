package com.example.photogallery.data

//import com.example.photogallery.network.AnimeApi
import com.example.photogallery.network.AnimeApiService
import com.example.photogallery.network.AnimePhoto
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

import kotlinx.serialization.InternalSerializationApi

interface AnimePhotosRepository {
    @OptIn(InternalSerializationApi::class)
    suspend fun getAnimePhotos(): List<AnimePhoto>
}

class NetworkAnimePhotosRepository(
    private val animeApiService: AnimeApiService
) : AnimePhotosRepository {
    @OptIn(InternalSerializationApi::class)
    override suspend fun getAnimePhotos(): List<AnimePhoto> {
        return coroutineScope {
            // 并发发起 10 个请求
            val deferredList = (1..10).map {
                async(IO) { animeApiService.getPhoto() }
            }
            // 等待所有请求完成并合并结果
            deferredList.awaitAll() // 如果每个请求返回一个列表，使用 flatten 展平
        }
    }
}


