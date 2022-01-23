package com.synaone.testwithings.presentation.detail.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.synaone.testwithings.presentation.browsing.models.UiImage
import com.synaone.testwithings.presentation.detail.DetailContentFragment


internal class DetailPagerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    private val adapterItems = mutableListOf<UiImage>()

    override fun createFragment(
        position: Int
    ): Fragment = DetailContentFragment.getInstance(
        adapterItems[position]
    )

    override fun getItemCount() = adapterItems.count()

    /**
     * Sets the adapter items.
     */
    internal fun setAdapterData(entries: List<UiImage>) {
        adapterItems.clear()
        adapterItems.addAll(entries)

        notifyDataSetChanged()
    }

}