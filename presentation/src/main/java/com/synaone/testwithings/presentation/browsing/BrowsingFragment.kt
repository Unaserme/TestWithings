package com.synaone.testwithings.presentation.browsing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.synaone.testwithings.presentation.R
import com.synaone.testwithings.presentation.browsing.adapter.BrowsingImageAdapter
import com.synaone.testwithings.presentation.browsing.models.UiImage
import com.synaone.testwithings.presentation.databinding.FragmentBrowsingBinding
import com.synaone.testwithings.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlin.math.min

@AndroidEntryPoint
class BrowsingFragment: Fragment() {

    private val browsingViewModel: BrowsingViewModel by viewModels()

    private lateinit var browsingImageAdapter: BrowsingImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentView: View

        FragmentBrowsingBinding.inflate(
            layoutInflater,
            container,
            false
        ).apply {

            fragmentView = root

            browsingImageAdapter = BrowsingImageAdapter(
                lastItemDisplayed = {
                    if (browsingViewModel.asMoreImages && browsingImageAdapter.itemCount > 1) {
                        browsingViewModel.loadMoreImages()
                    }
                },
                onFavoriteClicked = { id, isFavorite ->
                    browsingViewModel.updateFavoriteStatus(id, isFavorite)
                }
            )

            browsingRecyclerView.adapter = browsingImageAdapter

            setHasOptionsMenu(true)

            (activity as? MainActivity)?.let {
                it.supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }

            lifecycleScope.launchWhenResumed {
                browsingViewModel
                    .flowImages
                    .distinctUntilChanged()
                    .collect {
                        browsingImageAdapter
                            .submitList(it)

                        (activity as? MainActivity)?.let { mainActivity ->
                            // Passing the quantity twice is the correct thing to do.
                            // See here https://developer.android.com/guide/topics/resources/string-resource#Plurals
                            val images = it.filterIsInstance<UiImage>().size
                            mainActivity.supportActionBar?.title = context?.resources?.getQuantityString(
                                R.plurals.browsing_number,
                                images,
                                images
                            )
                        }
                    }
            }

            lifecycleScope.launchWhenResumed {
                browsingViewModel.loadMoreImages(true)
            }

            browsingSearchView.setOnQueryTextListener(
                object: SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {

                        query
                            ?.replace(" ", "+")
                            ?.substring(0, min(query.length, 99))
                            ?.takeIf { it.isNotBlank() }
                            ?.let {
                                browsingViewModel.loadMoreImages(true, query)
                            } ?: run {
                                browsingViewModel.loadMoreImages(true)
                            }

                        browsingSearchView.clearFocus()
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                       return when (newText.isNullOrBlank()) {
                            true -> {
                                browsingSearchView.clearFocus()
                                browsingViewModel.loadMoreImages(true)
                                true
                            }
                           false ->
                               false
                        }
                    }
                }
            )
        }

        return fragmentView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_browsing, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_filters -> {
                findNavController().navigate(BrowsingFragmentDirections.actionNavigationBrowsingToNavigationSettings())
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        }
    }
}