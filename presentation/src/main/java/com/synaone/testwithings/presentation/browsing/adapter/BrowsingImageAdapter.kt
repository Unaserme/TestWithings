package com.synaone.testwithings.presentation.browsing.adapter

import android.view.ViewGroup
import com.synaone.testwithings.presentation.R
import com.synaone.testwithings.presentation.browsing.models.UiImage
import com.synaone.testwithings.presentation.common.adapter.*
import com.synaone.testwithings.presentation.common.extensions.layoutInflater
import com.synaone.testwithings.presentation.databinding.AdapterImageItemBinding
import com.synaone.testwithings.presentation.databinding.AdapterNoImageItemBinding
import com.synaone.testwithings.presentation.databinding.AdapterNoMoreImageItemBinding

class BrowsingImageAdapter(
    val lastItemDisplayed: (() -> Unit),
    private val onFavoriteClicked: ((id: Int, isFavorite: Boolean) -> Unit),
): ImageListAdapter() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseImageViewHolder {

        return when (viewType) {
            IMAGE_VIEW_HOLDER ->
                ImageViewHolder(
                    AdapterImageItemBinding.inflate(
                        parent.layoutInflater,
                        parent,
                        false
                    )
                ).apply {
                    binding.imageItemFavoriteButton.setOnClickListener {
                        safeGetItem<UiImage>(adapterPosition)?.let { uiImage ->
                            onFavoriteClicked(uiImage.id, !uiImage.isFavorite.get())
                        }
                    }
                }
            NO_MORE_IMAGE_VIEW_HOLDER ->
                NoMoreImageViewHolder(
                    AdapterNoMoreImageItemBinding.inflate(
                        parent.layoutInflater,
                        parent,
                        false
                    )
                )
            NO_IMAGE_VIEW_HOLDER ->
                NoImageViewHolder(
                    R.string.browsing_no_image,
                    AdapterNoImageItemBinding.inflate(
                        parent.layoutInflater,
                        parent,
                        false
                    )
                )
            else ->
                throw Exception("This viewType is unknown: $viewType")
        }
    }

    override fun onBindViewHolder(holder: BaseImageViewHolder, position: Int) {
        val item = getItem(position)
        if (position == itemCount - 1) {
            lastItemDisplayed()
        }

        if (holder is ImageViewHolder && item is UiImage) {
            holder.bind(item)
        }
    }

    override fun onBindViewHolder(
        holder: BaseImageViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            payloads.filterIsInstance<PayloadValue>().forEach { payloadValue ->
                when (
                    payloadValue == PayloadValue.UPDATE_FAVOURITE
                            && holder.itemViewType == IMAGE_VIEW_HOLDER
                ) {
                    true -> {
                        (getItem(position) as? UiImage)?.let { uiImage ->
                            (holder as? ImageViewHolder)?.changeFavorite(uiImage.isFavorite.get())
                        }
                    }
                    false ->
                        super.onBindViewHolder(holder, position, payloads)
                }
            }
        }
    }

}

enum class PayloadValue {
    UPDATE_FAVOURITE,
    NONE
}