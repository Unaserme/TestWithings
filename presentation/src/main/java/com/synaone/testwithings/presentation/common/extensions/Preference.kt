package com.synaone.testwithings.presentation.common.extensions

import androidx.preference.MultiSelectListPreference

fun MultiSelectListPreference.setSummaryFromValues(
    values: Set<*>
) {
    values.filterIsInstance<String>().let { stringValues ->
        summary = stringValues.joinToString(", ") { entries[findIndexOfValue(it)] }
    }
}