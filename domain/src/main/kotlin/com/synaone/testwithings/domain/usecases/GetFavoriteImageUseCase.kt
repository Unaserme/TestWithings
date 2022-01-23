package com.synaone.testwithings.domain.usecases

import com.synaone.testwithings.domain.models.Image
import com.synaone.testwithings.domain.repositories.ImagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class GetFavoriteImageUseCase @Inject constructor(
    private val imagesRepository: ImagesRepository,
    private val getFavoriteImageIdUseCase: GetFavoriteImageIdUseCase,
) {
    operator fun invoke(): Flow<List<Image>> {

        return getFavoriteImageIdUseCase()
            .flatMapLatest { favoriteId ->
                imagesRepository
                    .getImages(
                        favoriteId
                    )
            }
    }
}
