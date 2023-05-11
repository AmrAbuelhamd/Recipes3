package com.blogspot.soyamr.data.db.recipe.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeEntity(
    @PrimaryKey val uuid: String,
    val name: String,
    val image: String,
    val lastUpdated: Long,
    val description: String,
    val instructions: String,
    val difficulty: Int,
)