package com.blogspot.soyamr.data.db.auth

import androidx.room.Entity
import androidx.room.PrimaryKey

const val SESSION_FIXED_ID = 1L

@Entity(tableName = "Sessions")
data class SessionEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long = SESSION_FIXED_ID,
    val type: SessionTypeEntity,
    val accessToken: String,
    val refreshToken: String,
    val phone: String,
)

enum class SessionTypeEntity {
    AUTHORIZED_USER, NONE
}