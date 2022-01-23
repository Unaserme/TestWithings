package com.synaone.testwithings.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class WsImagesRequest(
    val hits: List<WsImage>,
    val total: Int,
    val totalHits: Int,
)
