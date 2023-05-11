package com.blogspot.soyamr.data.db.common

import com.blogspot.soyamr.data.db.recipe.model.RecipeDetailsEntity
import com.blogspot.soyamr.data.db.recipe.model.RecipeEntity
import com.blogspot.soyamr.data.db.recipe.model.ShortRecipeEntity
import com.blogspot.soyamr.domain.recipe.model.Recipe
import com.blogspot.soyamr.domain.recipe.model.RecipeDetails
import com.blogspot.soyamr.domain.recipe.model.ShortRecipe

@Suppress("TooManyFunctions", "LargeClass")
object DbConverters {
    fun RecipeEntity.toDomain() = Recipe(
        uuid = uuid,
        name = name,
        image = image,
        lastUpdated = lastUpdated,
        description = description,
        instructions = instructions,
        difficulty = difficulty
    )

    fun Recipe.toEntity() = RecipeEntity(
        uuid = uuid,
        name = name,
        image = image,
        lastUpdated = lastUpdated,
        description = description,
        instructions = instructions,
        difficulty = difficulty
    )

    fun RecipeDetailsEntity.toDomain(similar: List<ShortRecipeEntity>) = RecipeDetails(
        uuid = uuid,
        name = name,
        images = images,
        lastUpdatedOnServer = lastUpdatedOnServer,
        lastUpdatedInCache = lastUpdatedInCache,
        description = description,
        instructions = instructions,
        difficulty = difficulty,
        similar = similar.map { it.toDomain() },
    )

    fun RecipeDetails.toEntity() = RecipeDetailsEntity(
        uuid = uuid,
        name = name,
        images = images,
        lastUpdatedOnServer = lastUpdatedOnServer,
        lastUpdatedInCache = lastUpdatedInCache,
        description = description,
        instructions = instructions,
        difficulty = difficulty,
        similar = similar.map { it.uuid }
    )

    fun ShortRecipe.toEntity() = ShortRecipeEntity(
        uuid = uuid,
        name = name,
        image = image,
    )

    fun ShortRecipeEntity.toDomain() = ShortRecipe(
        uuid = uuid,
        name = name,
        image = image,
    )
}
