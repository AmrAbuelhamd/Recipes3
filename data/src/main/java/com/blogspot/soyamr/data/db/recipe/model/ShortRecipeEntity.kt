package com.blogspot.soyamr.data.db.recipe.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShortRecipeEntity(
    @PrimaryKey val uuid: String,
    val name: String,
    val image: String,
)