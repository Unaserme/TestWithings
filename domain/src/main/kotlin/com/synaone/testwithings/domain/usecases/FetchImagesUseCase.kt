package com.synaone.testwithings.domain.usecases

import com.synaone.testwithings.core.Resource
import com.synaone.testwithings.domain.repositories.ImagesRepository
import javax.inject.Inject

class FetchImagesUseCase @Inject constructor(
    private val imagesRepository: ImagesRepository
) {

    suspend operator fun invoke(
        isSafeSearchEnable: Boolean,
        isEditorChoiceEnable: Boolean,
        selectedLanguage: String,
        selectedCategory: String?,
        selectedColor: List<String>,
        selectedOrder: String,
        selectedImageType: String?,
        query: String?,
        page: Int
    ): Resource<Boolean> {
        return imagesRepository.useRemoteImages(
            isSafeSearchEnable = isSafeSearchEnable,
            isEditorChoiceEnable = isEditorChoiceEnable,
            selectedLanguage = selectedLanguage,
            selectedCategory = selectedCategory,
            selectedColor = selectedColor,
            selectedOrder = selectedOrder,
            selectedImageType = selectedImageType ?: "all",
            query = query,
            page = page,
        )
    }
}