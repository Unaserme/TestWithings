package com.synaone.testwithings.presentation.favorite

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synaone.testwithings.domain.usecases.GetFavoriteImageUseCase
import com.synaone.testwithings.presentation.browsing.models.UiBaseImage
import com.synaone.testwithings.presentation.browsing.models.UiNoImage
import com.synaone.testwithings.presentation.browsing.models.toUiImage
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val application: Application,
    private val getFavoriteImageUseCase: GetFavoriteImageUseCase,
): ViewModel() {

    private val _flowImages = MutableStateFlow<List<UiBaseImage>>(emptyList())
    val flowImages: Flow<List<UiBaseImage>> = _flowImages

    fun loadImage() {

        viewModelScope.launch {

            getFavoriteImageUseCase()
                .collect { imageList ->

                    val uiImages: MutableList<UiBaseImage> = imageList
                        .toUiImage(
                            application,
                            emptyList()
                        )
                        .toMutableList()

                    if (uiImages.isEmpty()) {
                        uiImages
                            .add(
                                UiNoImage
                            )
                    }

                    _flowImages.emit(
                        uiImages
                    )

                }
        }

    }

}