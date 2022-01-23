package com.synaone.testwithings.presentation.favorite.adapter

import android.view.ViewGroup
import com.synaone.testwithings.presentation.R
import com.synaone.testwithings.presentation.browsing.models.UiImage
import com.synaone.testwithings.presentation.common.adapter.*
import com.synaone.testwithings.presentation.common.extensions.layoutInflater
import com.synaone.testwithings.presentation.databinding.AdapterImageItemBinding
import com.synaone.testwithings.presentation.databinding.AdapterNoImageItemBinding
import com.synaone.testwithings.presentation.databinding.AdapterNoMoreImageItemBinding

class FavoriteImageAdapter(
    private val onImageClicked: ((id: Int) -> Unit),
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
                    binding.root.setOnClickListener {
                        safeGetItem<UiImage>(adapterPosition)?.let { uiImage ->
                            onImageClicked(uiImage.id)
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
                    R.string.favorites_no_image,
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

    override fun onBindViewHolder(
        holder: BaseImageViewHolder,
        position: Int
    ) {
        val item = getItem(position)

        when ( holder is ImageViewHolder && item is UiImage) {
           true ->
                holder.bind(item, false)
            false -> {
                // Nothing to do
            }
        }
    }

}
