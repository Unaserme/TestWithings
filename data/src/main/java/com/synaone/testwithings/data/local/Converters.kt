package com.synaone.testwithings.data.local

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {

    /**
     * Converts a [String] into a [String] list.
     */
    @TypeConverter
    fun listStringFromString(value: String): List<String> {
        return Json.decodeFromString(value)
    }

    /**
     * Converts a [String] list into a [String].
     */
    @TypeConverter
    fun stringListToString(value: List<String>): String {
        return Json.encodeToString(value)
    }
}