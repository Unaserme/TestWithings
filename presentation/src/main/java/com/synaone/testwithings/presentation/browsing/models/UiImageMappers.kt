package com.synaone.testwithings.presentation.browsing.models

import android.content.Context
import androidx.annotation.StringRes
import androidx.databinding.ObservableBoolean
import com.synaone.testwithings.domain.models.Image
import com.synaone.testwithings.presentation.R

fun List<Image>.toUiImage(
    context: Context,
    favoriteId: List<Int>
): List<UiImage> {
    return map { image ->
        image.toUiImage(
            context,
            favoriteId.contains(image.id)
        )
    }
}

private fun Image.toUiImage(
    context: Context,
    isFavorite: Boolean
): UiImage {
    return UiImage(
        id = id,
        previewUrl = previewURL,
        views = views.toDisplayableString(context, DisplayableType.VIEWS),
        downloads = downloads.toDisplayableString(context, DisplayableType.DOWNLOADS),
        likes = likes.toDisplayableString(context, DisplayableType.LIKES),
        comments = comments.toDisplayableString(context, DisplayableType.COMMENTS),
        user = user,
        userImageURL = userImageURL,
        isFavorite = ObservableBoolean(isFavorite),
        webFormatUrl = webFormatURL,
    )
}

private fun Int.toDisplayableString(
    context: Context,
    displayableType: DisplayableType
): String {
    return context.getString(displayableType.displayableString, this)
}

private enum class DisplayableType(@StringRes val displayableString: Int) {
    VIEWS(R.string.detail_views),
    DOWNLOADS(R.string.detail_downloads),
    LIKES(R.string.detail_likes),
    COMMENTS(R.string.detail_comments)
}