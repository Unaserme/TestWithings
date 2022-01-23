package com.synaone.testwithings.presentation.common.extensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import coil.ImageLoader
import coil.imageLoader
import coil.request.ImageRequest
import coil.target.ImageViewTarget

inline fun ImageView.load(
    data: Any?,
    imageLoader: ImageLoader = context.imageLoader,
    crossinline onSuccessBlock: ((result: Drawable) -> Unit) = {},
    builder: ImageRequest.Builder.() -> Unit = {},
) {
    val target = object : ImageViewTarget(this) {
        override fun onSuccess(result: Drawable) {
            super.onSuccess(result)

            onSuccessBlock(result)
        }
    }

    val request = ImageRequest
        .Builder(context)
        .data(data)
        .target(target)
        .apply(builder)
        .build()

    val enqueuedRequest = when {
        (data == null) && (request.fallback != null) ->
            request
                .newBuilder(context)
                .data(request.fallback)
                .build()
        data == null ->
            null
        else ->
            request
    }

    enqueuedRequest?.let { imageLoader.enqueue(it) } ?: setImageDrawable(null)
}