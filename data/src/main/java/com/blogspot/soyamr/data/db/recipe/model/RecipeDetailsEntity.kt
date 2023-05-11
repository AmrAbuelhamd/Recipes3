package com.blogspot.soyamr.data.db.recipe.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeDetailsEntity(
    @PrimaryKey val uuid: String,
    val name: String,
    val images: List<String>,
    val lastUpdatedOnServer: Long,
    val lastUpdatedInCache: Long,
    val description: String?,
    val instructions: String,
    val difficulty: Int,
    val similar: List<String>
)