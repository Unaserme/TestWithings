package com.synaone.testwithings.data.repositories

import com.synaone.testwithings.core.Error
import com.synaone.testwithings.core.Loading
import com.synaone.testwithings.core.Resource
import com.synaone.testwithings.core.Success
import com.synaone.testwithings.data.local.models.ImageEntity
import com.synaone.testwithings.data.remote.models.WsImage
import com.synaone.testwithings.data.sources.ImageLocalDataSource
import com.synaone.testwithings.data.sources.ImagesRemoteDataSource
import com.synaone.testwithings.domain.models.Image
import com.synaone.testwithings.domain.repositories.ImagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    private val imagesRemoteDataSource: ImagesRemoteDataSource,
    private val imagesLocalDataSource: ImageLocalDataSource,
): ImagesRepository {

    private val _imagesNotCached = MutableStateFlow<List<Image>>(emptyList())
    override val imagesNotCached: Flow<List<Image>> = _imagesNotCached

    override suspend fun useRemoteImages(
        isSafeSearchEnable: Boolean,
        isEditorChoiceEnable: Boolean,
        selectedLanguage: String,
        selectedCategory: String?,
        selectedColor: List<String>,
        selectedOrder: String,
        selectedImageType: String,
        query: String?,
        page: Int,
    ): Resource<Boolean> {

        return when (val result =
            imagesRemoteDataSource.fetchImages(
                isSafeSearchEnable = isSafeSearchEnable,
                isEditorChoiceEnable = isEditorChoiceEnable,
                selectedLanguage = selectedLanguage,
                selectedCategory = selectedCategory,
                selectedColor = selectedColor.joinToString(","),
                selectedOrder = selectedOrder,
                selectedImageType = selectedImageType,
                query = query,
                page = page,
            )
        ) {
            is Success -> {

                val imageEntities = result.data.hits.map { wsImage ->
                    wsImage.toImageEntity(
                        isSafeSearchEnable,
                        isEditorChoiceEnable,
                        selectedLanguage,
                        listOfNotNull(selectedCategory),
                        selectedColor,
                        selectedImageType,
                    )
                }

                query?.let {
                    _imagesNotCached.emit(imageEntities)
                }

                imagesLocalDataSource.saveImages(imageEntities)

                Resource.success(result.data.total >= page * 100)

            }
            is Error -> {
                Resource.error(result.errorCode)
            }
            is Loading ->
                Resource.loading()
        }
    }

    override fun getImages(
        isSafeSearchEnable: Boolean,
        isEditorChoiceEnable: Boolean,
        selectedLanguage: String,
        selectedCategory: String?,
        selectedColor: List<String>,
        selectedOrder: String,
        selectedImageType: String?,
        isSearchEnable: Boolean,
    ): Flow<List<Image>> {
        return when (isSearchEnable) {
            true ->
                imagesNotCached
            false ->
                imagesLocalDataSource
                    .getImages(
                        isSafeSearchEnable,
                        isEditorChoiceEnable,
                        selectedLanguage,
                        selectedCategory,
                        selectedColor,
                        selectedOrder,
                        selectedImageType,
                    )
        }
    }

    override fun getImages(
        favoriteId: List<Int>
    ): Flow<List<Image>> {
        return imagesLocalDataSource
            .getImages(favoriteId)
    }

    private fun WsImage.toImageEntity(
        isSafeSearchEnable: Boolean,
        isEditorChoiceEnable: Boolean,
        selectedLanguage: String,
        selectedCategory: List<String>,
        selectedColor: List<String>,
        selectedImageType: String,
    ) = ImageEntity(
        id = id,
        previewURL = previewURL,
        tags = tags,
        views = views,
        downloads = downloads,
        likes = likes,
        comments = comments,
        user = user,
        userImageURL = userImageURL,
        category = selectedCategory,
        colors = selectedColor,
        editorChoice = isEditorChoiceEnable,
        language = listOf(selectedLanguage),
        safe = isSafeSearchEnable,
        imageType = selectedImageType.takeIf { it.equals("all", true).not() },
        webFormatURL = webFormatURL,
    )
}
