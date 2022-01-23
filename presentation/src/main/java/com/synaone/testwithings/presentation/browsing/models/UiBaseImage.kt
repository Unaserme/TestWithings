package com.synaone.testwithings.presentation.browsing.models

import androidx.databinding.ObservableBoolean
import java.io.Serializable

sealed class UiBaseImage

data class UiImage(
     val id: Int,
     val previewUrl: String,
     val views: String,
     val downloads: String,
     val likes: String,
     val comments: String,
     val user: String,
     val userImageURL: String,
     val isFavorite: ObservableBoolean,
     val webFormatUrl: String,
): UiBaseImage(), Serializable {
     fun isSameUiImageAs(secondImage: UiImage): Boolean {
          return (id == secondImage.id) && (previewUrl == secondImage.previewUrl)
     }
}

object UiNoImage: UiBaseImage()

object UiNoMoreImage: UiBaseImage()
