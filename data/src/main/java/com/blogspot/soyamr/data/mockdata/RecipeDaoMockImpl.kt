package com.blogspot.soyamr.data.mockdata

import com.blogspot.soyamr.data.db.recipe.RecipeDao
import com.blogspot.soyamr.data.db.recipe.model.RecipeDetailsEntity
import com.blogspot.soyamr.data.db.recipe.model.RecipeEntity
import com.blogspot.soyamr.data.db.recipe.model.ShortRecipeEntity
import com.blogspot.soyamr.domain.recipe.model.SortType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeDaoMockImpl @Inject constructor() : RecipeDao {
    override suspend fun insertRecipes(recipes: List<RecipeEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun insertRecipeDetails(recipe: RecipeDetailsEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun insertShortRecipes(recipes: List<ShortRecipeEntity>) {
        TODO("Not yet implemented")
    }

    override fun observeRecipes(query: String, sortType: SortType): Flow<List<RecipeEntity>> {
        TODO("Not yet implemented")
    }

    override fun observeRecipeDetails(id: String): Flow<RecipeDetailsEntity?> {
        TODO("Not yet implemented")
    }

    override fun observeShortRecipes(ids: List<String>): Flow<List<ShortRecipeEntity>> {
        TODO("Not yet implemented")
    }
}