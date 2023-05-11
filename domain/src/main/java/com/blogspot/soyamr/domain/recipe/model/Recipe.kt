package com.blogspot.soyamr.domain.recipe.model

data class Recipe(
    val uuid: String,
    val name: String,
    val image: String,
    val lastUpdated: Long,
    val description: String,
    val instructions: String,
    val difficulty: Int,
)