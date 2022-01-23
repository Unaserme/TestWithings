package com.synaone.testwithings.domain.usecases

import com.synaone.testwithings.domain.models.Image
import com.synaone.testwithings.domain.repositories.ImagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(
    private val imagesRepository: ImagesRepository,
) {

    operator fun invoke(
        isSafeSearchEnable: Boolean,
        isEditorChoiceEnable: Boolean,
        selectedLanguage: String,
        selectedCategory: String?,
        selectedColor: List<String>,
        selectedOrder: String,
        selectedImageType: String?,
        isSearchEnable: Boolean
    ): Flow<List<Image>> {
        return imagesRepository
            .getImages(
                isSafeSearchEnable,
                isEditorChoiceEnable,
                selectedLanguage,
                selectedCategory,
                selectedColor,
                selectedOrder,
                selectedImageType,
                isSearchEnable,
            )
    }
}