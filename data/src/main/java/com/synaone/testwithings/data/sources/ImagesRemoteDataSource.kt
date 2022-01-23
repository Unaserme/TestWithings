package com.synaone.testwithings.data.sources

import com.synaone.testwithings.core.Resource
import com.synaone.testwithings.data.remote.models.ERROR_SOCKET_TIMEOUT
import com.synaone.testwithings.data.remote.models.ERROR_UNKNOWN
import com.synaone.testwithings.data.remote.models.HTTP_ERROR_BASE
import com.synaone.testwithings.data.remote.models.WsImagesRequest
import com.synaone.testwithings.data.service.NetworkService
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class ImagesRemoteDataSource @Inject constructor(
    private val networkService: NetworkService
) {

    suspend fun fetchImages(
        isSafeSearchEnable: Boolean,
        isEditorChoiceEnable: Boolean,
        selectedLanguage: String,
        selectedCategory: String?,
        selectedColor: String,
        selectedOrder: String,
        selectedImageType: String,
        query: String?,
        page: Int,
    ): Resource<WsImagesRequest> {
        return try {
            Resource.success(
                networkService.getImages(
                    isSafeSearchEnable = isSafeSearchEnable,
                    isEditorChoiceEnable = isEditorChoiceEnable,
                    selectedLanguage = selectedLanguage,
                    selectedCategory = selectedCategory,
                    selectedColor = selectedColor,
                    selectedOrder = selectedOrder,
                    selectedImageType = selectedImageType,
                    query = query,
                    page = page,
                )
            )
        } catch (e: Exception) {
            when (e) {
                is HttpException ->
                    Resource.error(HTTP_ERROR_BASE + e.code())
                is SocketTimeoutException ->
                    Resource.error(ERROR_SOCKET_TIMEOUT)
                else ->
                    Resource.error(ERROR_UNKNOWN)
            }
        }
    }
}