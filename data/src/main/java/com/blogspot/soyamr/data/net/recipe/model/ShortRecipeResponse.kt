package com.blogspot.soyamr.data.net.recipe.model

import kotlinx.serialization.Serializable

@Serializable
data class ShortRecipeResponse(
    val uuid: String? = null,
    val name: String? = null,
    val image: String? = null,
)
