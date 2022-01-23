package com.synaone.testwithings.data.repositories

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.synaone.testwithings.domain.repositories.PreferenceRepository
import com.synaone.testwithings.domain.repositories.PreferenceRepository.Companion.PreferenceKey
import com.synaone.testwithings.domain.repositories.PreferenceRepository.Companion.PreferenceKey.FAVORITE_IMAGE_IDS
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class PreferenceRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context
): PreferenceRepository {

    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(context)

    /** JSON serialisation helper instance. */
    private val json = Json {
        ignoreUnknownKeys = true
    }

    private fun setIntList(key: String, value: List<Int>) {
        return preferences.edit {
            putString(key, json.encodeToString(value))
        }
    }

    /**
     * @return the int list matching the provided key.
     */
    private fun retrieveIntList(key: String): List<Int> {
        return preferences.getString(key, null)?.let {
            json.decodeFromString(it)
        } ?: emptyList()
    }

    private val favoriteImageIdsFlow: MutableStateFlow<List<Int>>
            by lazy {
                MutableStateFlow(
                    retrieveIntList(
                        FAVORITE_IMAGE_IDS.preferenceValue
                    )
                )
            }

    override fun isPreferenceEnable(preferenceKey: PreferenceKey): Boolean {
        return preferences.getBoolean(preferenceKey.preferenceValue, false)
    }

    override fun preferenceSelectedValue(preferenceKey: PreferenceKey): String? {
        return preferences.getString(preferenceKey.preferenceValue, null).takeIf { it.equals("all", true).not() }
    }

    override fun preferenceSelectedValues(preferenceKey: PreferenceKey): MutableSet<String>? {
        return preferences.getStringSet(preferenceKey.preferenceValue, null)
    }

    override fun getFavoriteImageIdAsFlow(): Flow<List<Int>> {
        return favoriteImageIdsFlow
    }

    override fun updateImageFavoriteStatus(imageId: Int, isFavorite: Boolean) {
        val nextList = when {
            favoriteImageIdsFlow.value.contains(imageId) && isFavorite -> favoriteImageIdsFlow.value
            isFavorite -> favoriteImageIdsFlow.value + imageId
            else -> favoriteImageIdsFlow.value - imageId
        }

        setIntList(FAVORITE_IMAGE_IDS.preferenceValue, nextList)
        favoriteImageIdsFlow.compareAndSet(favoriteImageIdsFlow.value, nextList)
    }

}