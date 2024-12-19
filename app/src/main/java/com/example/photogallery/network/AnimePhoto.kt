package com.example.photogallery.network

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@InternalSerializationApi @Serializable
data class AnimePhoto(
    val id: Int,
    @SerialName(value = "img_src")
    val imgSrc: String
)