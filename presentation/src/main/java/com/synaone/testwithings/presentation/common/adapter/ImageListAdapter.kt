package com.synaone.testwithings.presentation.common.adapter

import androidx.annotation.StringRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.synaone.testwithings.presentation.browsing.adapter.PayloadValue
import com.synaone.testwithings.presentation.browsing.models.UiBaseImage
import com.synaone.testwithings.presentation.browsing.models.UiImage
import com.synaone.testwithings.presentation.browsing.models.UiNoImage
import com.synaone.testwithings.presentation.browsing.models.UiNoMoreImage

abstract class ImageListAdapter: ListAdapter<UiBaseImage, BaseImageViewHolder>(
    DIFF_UTIL
) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UiImage ->
                IMAGE_VIEW_HOLDER
            is UiNoMoreImage ->
                NO_MORE_IMAGE_VIEW_HOLDER
            is UiNoImage ->
                NO_IMAGE_VIEW_HOLDER
            else ->
                -1
        }
    }

    /**
     * Safer version of the getItem method, returning null when the item could not be found, or when the item has the inccorect class.
     */
    protected inline fun <reified T> safeGetItem(
        position: Int
    ): T? {
        return try {
            getItem(position) as? T
        } catch (e: Exception) {
            null
        }
    }

    companion object {

        const val IMAGE_VIEW_HOLDER = 0
        const val NO_MORE_IMAGE_VIEW_HOLDER = 1
        const val NO_IMAGE_VIEW_HOLDER = 2

        val DIFF_UTIL = object : DiffUtil.ItemCallback<UiBaseImage>() {

            override fun areItemsTheSame(oldItem: UiBaseImage, newItem: UiBaseImage): Boolean {
                return when {
                    oldItem is UiImage && newItem is UiImage ->
                        oldItem.id == newItem.id
                    oldItem is UiNoMoreImage && newItem is UiNoMoreImage ->
                        true
                    oldItem is UiNoImage && newItem is UiNoImage ->
                        true
                    else ->
                        false
                }
            }

            override fun areContentsTheSame(oldItem: UiBaseImage, newItem: UiBaseImage): Boolean {
                return when {
                    oldItem is UiImage && newItem is UiImage ->
                        oldItem.isSameUiImageAs(newItem) && oldItem.isFavorite.get() == newItem.isFavorite.get()
                    oldItem is UiNoMoreImage && newItem is UiNoMoreImage ->
                        true
                    oldItem is UiNoImage && newItem is UiNoImage ->
                        true
                    else ->
                        false
                }
            }

            override fun getChangePayload(oldItem: UiBaseImage, newItem: UiBaseImage): PayloadValue {
                return when {
                    ((oldItem is UiImage && newItem is UiImage)
                            && (oldItem.isSameUiImageAs(newItem) && oldItem.isFavorite != newItem.isFavorite)) ->
                        PayloadValue.UPDATE_FAVOURITE
                    else ->
                        PayloadValue.NONE
                }
            }
        }
    }
}