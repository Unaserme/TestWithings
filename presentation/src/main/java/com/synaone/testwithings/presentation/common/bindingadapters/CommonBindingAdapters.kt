package com.synaone.testwithings.presentation.common.bindingadapters

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.synaone.testwithings.presentation.common.extensions.load

@BindingAdapter(
    value = ["imageUrl", "fallback"],
    requireAll = false
)
fun setImageUrl(
    view: ImageView,
    imageUrl: String?,
    fallback: Any?,
) {
    view.load(
        data = imageUrl.takeUnless { it.isNullOrBlank() },
        onSuccessBlock = { drawable ->
            if (drawable.intrinsicWidth == drawable.intrinsicHeight) {
                view.scaleType = ImageView.ScaleType.FIT_CENTER
            }
        },
    ) {

        crossfade(300)

        when (fallback) {
            is Int -> {
                fallback(fallback)
                error(fallback)
            }

            is Drawable -> {
                fallback(fallback)
                error(fallback)
            }
        }
    }
}

/**
 * Changes the provided view visibility.
 */
@BindingAdapter("isVisible")
fun setVisible(
    view: View, isVisible: Boolean
) {
    view.isVisible = isVisible
}
