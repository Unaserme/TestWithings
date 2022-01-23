package com.synaone.testwithings.domain.repositories

import com.synaone.testwithings.core.Resource
import com.synaone.testwithings.domain.models.Image
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {

    val imagesNotCached: Flow<List<Image>>

    suspend fun useRemoteImages(
        isSafeSearchEnable: Boolean,
        isEditorChoiceEnable: Boolean,
        selectedLanguage: String,
        selectedCategory: String?,
        selectedColor: List<String>,
        selectedOrder: String,
        selectedImageType: String,
        query: String?,
        page: Int,
    ): Resource<Boolean>

    fun getImages(
        isSafeSearchEnable: Boolean,
        isEditorChoiceEnable: Boolean,
        selectedLanguage: String,
        selectedCategory: String?,
        selectedColor: List<String>,
        selectedOrder: String,
        selectedImageType: String?,
        isSearchEnable: Boolean,
    ): Flow<List<Image>>

    fun getImages(
        favoriteId: List<Int>
    ): Flow<List<Image>>
}