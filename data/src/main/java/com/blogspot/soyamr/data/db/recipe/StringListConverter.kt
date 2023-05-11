package com.blogspot.soyamr.data.db.recipe

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class StringListConverter {
    @TypeConverter
    fun fromJson(json: String): List<String> {
        return try {
            Json.decodeFromString(json)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    @TypeConverter
    fun toJson(items: List<String>): String {
        return try {
            Json.encodeToString(items)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}