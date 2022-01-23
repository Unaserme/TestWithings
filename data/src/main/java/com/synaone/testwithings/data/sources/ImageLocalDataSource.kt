package com.synaone.testwithings.data.sources

import com.synaone.testwithings.data.local.ApplicationDatabase
import com.synaone.testwithings.data.local.models.ImageEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class ImageLocalDataSource @Inject constructor(
    private val database: ApplicationDatabase
){

    suspend fun saveImages(imageEntities: List<ImageEntity>) {
        database.imagesDao().insertOrUpdateImages(imageEntities)
    }

    fun getImages(
        isSafeSearchEnable: Boolean,
        isEditorChoiceEnable: Boolean,
        selectedLanguage: String,
        selectedCategory: String?,
        selectedColor: List<String>,
        selectedOrder: String,
        selectedImageType: String?,
    ): Flow<List<ImageEntity>> {
        return when {
            selectedOrder.equals(POPULAR_ORDER, true) ->
                database
                    .imagesDao()
                    .getPopularImages(
                        isSafeSearchEnable,
                        isEditorChoiceEnable,
                        selectedLanguage.toDataBaseString(),
                        selectedCategory?.toDataBaseString(),
                        selectedColor.toDataBaseString(),
                        selectedImageType,
                    )
            else ->
                database
                    .imagesDao()
                    .getLatestImages(
                        isSafeSearchEnable,
                        isEditorChoiceEnable,
                        selectedLanguage.toDataBaseString(),
                        selectedCategory?.toDataBaseString(),
                        selectedColor.toDataBaseString(),
                        selectedImageType,
                    )
        }.distinctUntilChanged()
    }

    fun getImages(
        favoriteId: List<Int>
    ): Flow<List<ImageEntity>> {
        return database
            .imagesDao()
            .getImages(
                favoriteId
            )
    }


    companion object {
        private fun List<String>.toDataBaseString() = "%${this.joinToString(",")}%"
        private fun String.toDataBaseString() = "%$this%"

        const val POPULAR_ORDER = "popular"
    }
}