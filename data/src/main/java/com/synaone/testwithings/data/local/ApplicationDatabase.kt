package com.synaone.testwithings.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.synaone.testwithings.data.local.dao.ImagesDao
import com.synaone.testwithings.data.local.models.ImageEntity

@Database(
    entities = [
        ImageEntity::class
    ],
    version = 1,
)
abstract class ApplicationDatabase: RoomDatabase() {
    abstract fun imagesDao(): ImagesDao
}