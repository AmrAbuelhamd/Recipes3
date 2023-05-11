package com.blogspot.soyamr.recipes3.presentation.common

import com.blogspot.soyamr.domain.recipe.model.Recipe
import com.blogspot.soyamr.domain.recipe.model.RecipeDetails
import com.blogspot.soyamr.domain.recipe.model.ShortRecipe
import com.blogspot.soyamr.recipes3.presentation.common.utils.DateTimeUtils
import com.blogspot.soyamr.recipes3.presentation.recipeDetails.models.RecipeDetailsUi
import com.blogspot.soyamr.recipes3.presentation.recipeDetails.models.ShortRecipeUi
import com.blogspot.soyamr.recipes3.presentation.recipeList.models.RecipeUi

object UiConverters {
    fun Recipe.toUi() = RecipeUi(
        id = uuid,
        name = name,
        image = image,
        lastUpdated = DateTimeUtils.formatEpochTime(lastUpdated, DateTimeUtils.DateFormatType.SHORT),
        description = description,
        difficulty = difficulty,
        instructions = instructions
    )

    fun RecipeDetails.toUi() = RecipeDetailsUi(
        name = name,
        lastUpdatedOnServer = DateTimeUtils.formatEpochTime(lastUpdatedOnServer, DateTimeUtils.DateFormatType.FULL),
        description = description,
        instructions = instructions,
        difficulty = difficulty
    )

    fun ShortRecipe.toUi() = ShortRecipeUi(
        id = uuid,
        name = name,
        image = image
    )
}