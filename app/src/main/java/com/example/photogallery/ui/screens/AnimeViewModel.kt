package com.example.photogallery.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.IOException
import com.example.photogallery.network.AnimeApi
import kotlinx.serialization.InternalSerializationApi

sealed interface AnimeUiState {
    data class Success(val photos: String) : AnimeUiState
    object Error : AnimeUiState
    object Loading : AnimeUiState
}

class AnimeViewModel : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var animeUiState: AnimeUiState by mutableStateOf(AnimeUiState.Loading)
        private set

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getAnimePhotos()
    }

    /**
     * Gets Animes photos information from the Animes API Retrofit service and updates the
     *
     */
    @OptIn(InternalSerializationApi::class)
    fun getAnimePhotos() {
        viewModelScope.launch {
            try {
                val listResult = AnimeApi.retrofitService.getPhotos()
                animeUiState = AnimeUiState.Success(
                    "Success: ${listResult.size} Mars photos retrieved"
                )
            } catch (e: IOException) {
                animeUiState = AnimeUiState.Error
            }
        }
    }
}
