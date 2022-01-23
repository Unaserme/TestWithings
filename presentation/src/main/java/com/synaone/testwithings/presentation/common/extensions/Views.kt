package com.synaone.testwithings.presentation.common.extensions

import android.view.LayoutInflater
import android.view.View

/**
 * @return the layout inflater associated to the current view.
 */
val <T> T.layoutInflater: LayoutInflater where T : View
    get() = LayoutInflater.from(context)