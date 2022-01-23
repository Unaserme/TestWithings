package com.synaone.testwithings.data.service

import com.synaone.testwithings.data.remote.models.WsImagesRequest
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("/api/")
    suspend fun getImages(
        @Query("safesearch") isSafeSearchEnable: Boolean,
        @Query("editors_choice") isEditorChoiceEnable: Boolean,
        @Query("lang") selectedLanguage: String,
        @Query("category") selectedCategory: String?,
        @Query("colors") selectedColor: String,
        @Query("order") selectedOrder: String,
        @Query("image_type") selectedImageType: String,
        @Query("q") query: String?,
        @Query("per_page") perPage: Int = 100,
        @Query("page") page: Int,
    ): WsImagesRequest
}