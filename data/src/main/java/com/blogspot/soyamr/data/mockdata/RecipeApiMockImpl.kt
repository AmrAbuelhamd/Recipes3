package com.blogspot.soyamr.data.mockdata

import com.blogspot.soyamr.data.net.recipe.RecipeApi
import com.blogspot.soyamr.data.net.recipe.model.RecipeDetailsResponse
import com.blogspot.soyamr.data.net.recipe.model.RecipeDetailsResponseWrapper
import com.blogspot.soyamr.data.net.recipe.model.RecipeResponse
import com.blogspot.soyamr.data.net.recipe.model.RecipesListResponseWrapper
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

class RecipeApiMockImpl @Inject constructor() : RecipeApi {

    override suspend fun getRecipes(): RecipesListResponseWrapper {
        delay(1000)
        return RecipesListResponseWrapper(
            List(10) { index ->
                RecipeResponse(
                    uuid = index.toString(),
                    name = "name $index",
                    images = listOf("image $index"),
                    lastUpdated = Random.nextLong(),
                    description = "description $index",
                    instructions = "instructions $index",
                    difficulty = index
                )
            }
        )
    }

    override suspend fun getRecipeDetails(uuid: String): RecipeDetailsResponseWrapper {
        delay(1000)
        return RecipeDetailsResponseWrapper(
            RecipeDetailsResponse(
                uuid = uuid,
                name = "name $uuid",
                images = listOf("image $uuid"),
                lastUpdated = Random.nextLong(),
                description = "description $uuid",
                instructions = "instructions $uuid",
                difficulty = Random.nextInt(),
            )
        )
    }
}