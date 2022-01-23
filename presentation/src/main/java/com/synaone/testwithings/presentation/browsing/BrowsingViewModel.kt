package com.synaone.testwithings.presentation.browsing

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synaone.testwithings.core.Success
import com.synaone.testwithings.domain.repositories.PreferenceRepository
import com.synaone.testwithings.domain.repositories.PreferenceRepository.Companion.PreferenceKey.*
import com.synaone.testwithings.domain.usecases.FetchImagesUseCase
import com.synaone.testwithings.domain.usecases.GetFavoriteImageIdUseCase
import com.synaone.testwithings.domain.usecases.GetImagesUseCase
import com.synaone.testwithings.domain.usecases.UpdateFavoriteImageIdUseCase
import com.synaone.testwithings.presentation.browsing.models.UiBaseImage
import com.synaone.testwithings.presentation.browsing.models.UiNoImage
import com.synaone.testwithings.presentation.browsing.models.UiNoMoreImage
import com.synaone.testwithings.presentation.browsing.models.toUiImage
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrowsingViewModel @Inject constructor(
    private val application: Application,
    private val fetchImagesUseCase: FetchImagesUseCase,
    private val getImagesUseCase: GetImagesUseCase,
    private val getFavoriteImageIdUseCase: GetFavoriteImageIdUseCase,
    private val updateFavoriteImageIdUseCase: UpdateFavoriteImageIdUseCase,
    private val preferenceRepository: PreferenceRepository,
): ViewModel() {

    private val _flowImages = MutableStateFlow<List<UiBaseImage>>(emptyList())
    val flowImages: Flow<List<UiBaseImage>> = _flowImages

    private var currentJob: Job? = null

    private var currentPage: Int = 0
    var asMoreImages: Boolean = true

    fun loadMoreImages(
        resetPageNumber: Boolean = false,
        query: String? = null
    ) {

        currentPage = when (resetPageNumber) {
            true ->
                1
            false ->
                currentPage + 1
        }

        val isSafeSearchEnable = preferenceRepository.isPreferenceEnable(SAFE_SEARCH)
        val isEditorChoiceEnable = preferenceRepository.isPreferenceEnable(EDITOR_CHOICE)
        val selectedLanguage = preferenceRepository.preferenceSelectedValue(LANGUAGE) ?: "en"
        val selectedCategory = preferenceRepository.preferenceSelectedValue(CATEGORY)
        val selectedColor = preferenceRepository.preferenceSelectedValues(COLOR)?.toList() ?: emptyList()
        val selectedOrder = preferenceRepository.preferenceSelectedValue(ORDER) ?: "popular"
        val selectedImageType = preferenceRepository.preferenceSelectedValue(IMAGE_TYPE)

        viewModelScope.launch {
            val result = fetchImagesUseCase(
                isSafeSearchEnable = isSafeSearchEnable,
                isEditorChoiceEnable = isEditorChoiceEnable,
                selectedLanguage = selectedLanguage,
                selectedCategory = selectedCategory,
                selectedColor = selectedColor,
                selectedOrder = selectedOrder,
                selectedImageType = selectedImageType,
                query = query,
                page = currentPage,
            )

            (result as? Success)?.let {
                asMoreImages = result.data
            }
        }

        currentJob?.cancel()

        currentJob = viewModelScope.launch {
            combine(
                getImagesUseCase(
                    isSafeSearchEnable = isSafeSearchEnable,
                    isEditorChoiceEnable = isEditorChoiceEnable,
                    selectedLanguage = selectedLanguage,
                    selectedCategory = selectedCategory,
                    selectedColor = selectedColor,
                    selectedOrder = selectedOrder,
                    selectedImageType = selectedImageType,
                    isSearchEnable = query != null,
                ),
                getFavoriteImageIdUseCase()
            ) { imageList, favoriteId ->

                val uiImages: MutableList<UiBaseImage> = imageList
                    .toUiImage(
                        application,
                        favoriteId
                    )
                    .toMutableList()

                when {
                    !asMoreImages ->
                        uiImages
                            .add(
                                UiNoMoreImage
                            )
                    uiImages.isEmpty() ->
                        uiImages
                            .add(
                                UiNoImage
                            )
                }

                uiImages
            }.collect { browsingImageList ->
                _flowImages.emit(
                    browsingImageList
                )
            }
        }
    }

    fun updateFavoriteStatus(
        id: Int,
        isFavorite: Boolean
    ) {
        updateFavoriteImageIdUseCase(
            id,
            isFavorite
        )
    }


}