package com.example.photogallery.data

//import com.example.photogallery.network.AnimeApi
import com.example.photogallery.network.AnimeApiService
import com.example.photogallery.network.AnimePhoto
import kotlinx.coroutines.Dispatchers
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
            val totalImages = 20
            var fetchedImages = 0
            val results = mutableListOf<AnimePhoto>()

            while (fetchedImages < totalImages) {
                val photos = animeApiService.getPhotos()

                // Check if the API returned enough data
                if (photos.isEmpty()) {
                    break // Stop fetching if no more data is returned
                }

                // Determine how many images can be added in this batch
                val remainingImages = totalImages - fetchedImages
                val photosToAdd = photos.take(minOf(remainingImages, photos.size))

                results.addAll(photosToAdd)
                fetchedImages += photosToAdd.size
            }

            results
        }
    }
}

