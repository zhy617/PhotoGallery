package com.example.photogallery.network

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@InternalSerializationApi
@Serializable
data class AnimePhoto(
    val code: Int,
    @SerialName(value = "imgurl")
    val imgSrc: String,
    val width: Int,
    val height: Int,
)