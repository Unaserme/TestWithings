package com.synaone.testwithings.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.synaone.testwithings.presentation.R
import com.synaone.testwithings.presentation.browsing.models.UiImage
import com.synaone.testwithings.presentation.databinding.FragmentFavoriteBinding
import com.synaone.testwithings.presentation.favorite.adapter.FavoriteImageAdapter
import com.synaone.testwithings.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class FavoriteFragment: Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModels()

    private lateinit var favoriteImageAdapter: FavoriteImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val fragmentView: View

        FragmentFavoriteBinding.inflate(
            layoutInflater,
            container,
            false
        ).apply {

            fragmentView = root

            favoriteViewModel.loadImage()

            favoriteImageAdapter = FavoriteImageAdapter {
                findNavController().navigate(FavoriteFragmentDirections.actionNavigationFavoritesToNavigationDetail(it))
            }
            favoriteRecyclerView.adapter = favoriteImageAdapter

            lifecycleScope.launchWhenResumed {
                favoriteViewModel
                    .flowImages
                    .collect { imageList ->
                        favoriteImageAdapter
                            .submitList(imageList)

                        (activity as? MainActivity)?.let { mainActivity ->
                            // Passing the quantity twice is the correct thing to do.
                            // See here https://developer.android.com/guide/topics/resources/string-resource#Plurals
                            val images = imageList.filterIsInstance<UiImage>().size
                            mainActivity.supportActionBar?.title = context?.resources?.getQuantityString(
                                R.plurals.favorites_number,
                                images,
                                images
                            )
                        }
                    }
            }
        }

        return fragmentView
    }
}