package com.synaone.testwithings.domain.repositories

import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {

    fun isPreferenceEnable(preferenceKey: PreferenceKey): Boolean

    fun preferenceSelectedValue(preferenceKey: PreferenceKey): String?

    fun preferenceSelectedValues(preferenceKey: PreferenceKey): MutableSet<String>?

    fun getFavoriteImageIdAsFlow(): Flow<List<Int>>

    fun updateImageFavoriteStatus(imageId: Int, isFavorite: Boolean)

    companion object {
        enum class PreferenceKey(val preferenceValue: String) {
            SAFE_SEARCH("safe_search"),
            EDITOR_CHOICE("editor_choice"),
            LANGUAGE("language"),
            CATEGORY("category"),
            COLOR("color"),
            ORDER("order"),
            IMAGE_TYPE("image_type"),
            FAVORITE_IMAGE_IDS("favorite_image_ids"),
        }
    }
}