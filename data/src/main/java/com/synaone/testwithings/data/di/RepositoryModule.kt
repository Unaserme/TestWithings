package com.synaone.testwithings.data.di

import com.synaone.testwithings.data.repositories.ImagesRepositoryImpl
import com.synaone.testwithings.data.repositories.PreferenceRepositoryImpl
import com.synaone.testwithings.domain.repositories.ImagesRepository
import com.synaone.testwithings.domain.repositories.PreferenceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindImagesRepository(
        repositoryImpl: ImagesRepositoryImpl
    ): ImagesRepository

    @Binds
    @Singleton
    abstract fun bindPreferenceRepository(
        preferenceRepositoryImpl: PreferenceRepositoryImpl
    ): PreferenceRepository
}