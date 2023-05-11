package com.blogspot.soyamr.data.net.common

import com.blogspot.soyamr.data.net.common.model.ApiErrorResponse
import com.blogspot.soyamr.data.net.common.model.ApiSuccessResponse
import com.blogspot.soyamr.data.net.common.model.ErrorItemResponse
import com.blogspot.soyamr.data.net.recipe.model.RecipeDetailsResponse
import com.blogspot.soyamr.data.net.recipe.model.RecipeResponse
import com.blogspot.soyamr.data.net.recipe.model.ShortRecipeResponse
import com.blogspot.soyamr.domain.common.extentions.orDefault
import com.blogspot.soyamr.domain.common.model.ApiError
import com.blogspot.soyamr.domain.common.model.ApiSuccess
import com.blogspot.soyamr.domain.common.model.ErrorItem
import com.blogspot.soyamr.domain.recipe.model.Recipe
import com.blogspot.soyamr.domain.recipe.model.RecipeDetails
import com.blogspot.soyamr.domain.recipe.model.ShortRecipe

@Suppress("TooManyFunctions", "LargeClass")
object NetConverters {

    fun ApiErrorResponse?.toDomain() = ApiError(
        extraMessage = this?.extraMessage.orEmpty(),
        code = this?.code.orDefault(),
        message = this?.message.orEmpty(),
        violations = this?.violations?.map {
            it.toDomain()
        }.orEmpty(),
        key = this?.key.orEmpty(),
        email = this?.mail.orEmpty()
    )

    fun ErrorItemResponse?.toDomain() = ErrorItem(
        propertyPath = this?.propertyPath.orEmpty(),
        key = this?.key.orEmpty(),
        message = this?.message.orEmpty()
    )

    fun ApiSuccessResponse.toDomain() = ApiSuccess(
        message = message.orEmpty()
    )

    fun RecipeDetailsResponse?.toDomain() = RecipeDetails(
        uuid = this?.uuid.orEmpty(),
        name = this?.name.orEmpty(),
        images = this?.images.orEmpty(),
        lastUpdatedOnServer = this?.lastUpdated.orDefault(),
        lastUpdatedInCache = System.currentTimeMillis(),
        description = this?.description.orEmpty(),
        instructions = this?.instructions.orEmpty(),
        difficulty = this?.difficulty.orDefault(),
        similar = this?.similar?.map { it.toDomain() }.orEmpty()
    )

    fun ShortRecipeResponse.toDomain() = ShortRecipe(
        uuid = uuid.orEmpty(),
        name = name.orEmpty(),
        image = image.orEmpty()
    )

    fun RecipeResponse.toDomain() = Recipe(
        uuid = uuid.orEmpty(),
        name = name.orEmpty(),
        image = images?.firstOrNull().orEmpty(),
        lastUpdated = lastUpdated.orDefault(),
        description = description.orEmpty(),
        instructions = instructions.orEmpty(),
        difficulty = difficulty.orDefault()
    )
}