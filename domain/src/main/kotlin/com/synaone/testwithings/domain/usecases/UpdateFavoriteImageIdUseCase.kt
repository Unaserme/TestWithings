package com.synaone.testwithings.domain.usecases

import com.synaone.testwithings.domain.repositories.PreferenceRepository
import javax.inject.Inject

class UpdateFavoriteImageIdUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    operator fun invoke(
        imageId: Int,
        isFavorite: Boolean
    ) {
        preferenceRepository.updateImageFavoriteStatus(
            imageId,
            isFavorite
        )
    }
}
