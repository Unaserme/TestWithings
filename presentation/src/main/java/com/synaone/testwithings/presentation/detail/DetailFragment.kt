package com.synaone.testwithings.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.synaone.testwithings.presentation.browsing.models.UiImage
import com.synaone.testwithings.presentation.databinding.FragmentDetailBinding
import com.synaone.testwithings.presentation.detail.adapter.DetailPagerAdapter
import com.synaone.testwithings.presentation.favorite.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class DetailFragment: Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModels()

    private val arguments: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentView: View

        FragmentDetailBinding.inflate(
            layoutInflater,
            container,
            false
        ).apply {
            fragmentView = root

            val detailPagerAdapter = DetailPagerAdapter(this@DetailFragment)
            detailViewPager.adapter = detailPagerAdapter

            favoriteViewModel.loadImage()

            lifecycleScope.launchWhenResumed {
                favoriteViewModel
                    .flowImages
                    .collect { imageList ->

                        val images = imageList.filterIsInstance<UiImage>()
                        detailPagerAdapter.setAdapterData(
                            images
                        )

                        images.indexOfFirst { it.id == arguments.currentId }.takeIf { it > 0 }?.let {
                            detailViewPager.setCurrentItem(it, false)
                        }
                    }
            }
        }

        return fragmentView
    }
}