package com.blogspot.soyamr.data.db.common

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

abstract class ListConverter<T> {
    @TypeConverter
    fun fromJson(json: String): List<T> {
        return try {
            Json.decodeFromString(json)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    @TypeConverter
    fun toJson(services: List<T>): String {
        return try {
            Json.encodeToString(services)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}