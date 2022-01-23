package com.synaone.testwithings.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WsImage(
    val id: Int,
    val tags: String,
    val previewURL: String,
    val views: Int,
    val downloads: Int,
    val likes: Int,
    val comments: Int,
    val user: String,
    val userImageURL: String,
    @SerialName("webformatURL")
    val webFormatURL: String
)
