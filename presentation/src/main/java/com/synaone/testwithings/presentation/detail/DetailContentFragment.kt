package com.synaone.testwithings.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.synaone.testwithings.presentation.browsing.models.UiImage
import com.synaone.testwithings.presentation.databinding.FragmentDetailContentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailContentFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentView: View

        FragmentDetailContentBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            fragmentView = root

            (requireArguments().get(ARG_UI_IMAGE) as? UiImage)?.let {
                uiImage = it
            }
        }

        return fragmentView
    }

    companion object {

        /** Fragment argument: image. */
        private const val ARG_UI_IMAGE = "ui_image"

        /**
         * Builds a DetailContentFragment instance.
         */
        fun getInstance(
            uiImage: UiImage
        ): Fragment {
            return DetailContentFragment().apply {
                arguments = bundleOf(Pair(ARG_UI_IMAGE, uiImage))
            }
        }
    }
}