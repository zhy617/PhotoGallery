package com.example.photogallery.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.photogallery.AnimePhotosApplication
import com.example.photogallery.data.AnimePhotosRepository
import com.example.photogallery.data.NetworkAnimePhotosRepository
import com.example.photogallery.network.AnimePhoto
import kotlinx.coroutines.launch
import java.io.IOException

import kotlinx.serialization.InternalSerializationApi
import retrofit2.HttpException

sealed interface AnimeUiState {
    data class Success @OptIn(InternalSerializationApi::class) constructor(val photos: AnimePhoto) :
        AnimeUiState

    object Error : AnimeUiState
    object Loading : AnimeUiState
}


class AnimeViewModel(private val animePhotosRepository: AnimePhotosRepository) : ViewModel() {
    /** 伴生对象 */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AnimePhotosApplication)
                val animePhotosRepository = application.container.animePhotosRepository
                AnimeViewModel(animePhotosRepository = animePhotosRepository)
            }
        }
    }

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
     * Gets Anime photos information from the Anime API Retrofit service and updates the
     *
     */
    @OptIn(InternalSerializationApi::class)
    fun getAnimePhotos() {
        viewModelScope.launch {
            animeUiState = try {
                val listResult = animePhotosRepository.getAnimePhotos()
                val result = listResult[0]
                AnimeUiState.Success(
                    result
                )
            } catch (e: IOException) {
                AnimeUiState.Error
            } catch (e: HttpException) {
                AnimeUiState.Error
            }
        }
    }
}
