package com.synaone.testwithings.presentation.common.adapter

import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.synaone.testwithings.presentation.browsing.models.UiImage
import com.synaone.testwithings.presentation.databinding.AdapterImageItemBinding
import com.synaone.testwithings.presentation.databinding.AdapterNoImageItemBinding
import com.synaone.testwithings.presentation.databinding.AdapterNoMoreImageItemBinding

sealed class BaseImageViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView)

class NoMoreImageViewHolder(
    binding: AdapterNoMoreImageItemBinding
): BaseImageViewHolder(binding.root)

class NoImageViewHolder(
    @StringRes noImageRes: Int,
    binding: AdapterNoImageItemBinding
): BaseImageViewHolder(binding.root) {
    init {
        binding.noImageRes = noImageRes
    }
}

class ImageViewHolder(
    val binding: AdapterImageItemBinding
): BaseImageViewHolder(binding.root) {

    fun bind(
        uiImage: UiImage,
        displayFavorite: Boolean = true
    ) {
        binding.uiImage = uiImage
        binding.isFavorite = uiImage.isFavorite.get()
        binding.isFavoriteDisplayed = displayFavorite
    }

    fun changeFavorite(isFavorite: Boolean) {
        binding.isFavorite = isFavorite
    }
}