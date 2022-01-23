package com.synaone.testwithings.domain.usecases

import com.synaone.testwithings.domain.repositories.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteImageIdUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    operator fun invoke(): Flow<List<Int>> {
        return preferenceRepository.getFavoriteImageIdAsFlow()
    }
}
