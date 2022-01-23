package com.synaone.testwithings.data.di

import android.content.Context
import androidx.room.Room
import com.synaone.testwithings.data.local.ApplicationDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    private val databaseName = "database"

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ApplicationDatabase {
        return Room
            .databaseBuilder(
                context,
                ApplicationDatabase::class.java,
                databaseName
            )
            .build()
    }
}