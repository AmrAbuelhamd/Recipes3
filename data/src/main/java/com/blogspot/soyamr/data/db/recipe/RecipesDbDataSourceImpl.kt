package com.blogspot.soyamr.data.db.recipe

import com.blogspot.soyamr.data.db.common.DbConverters.toDomain
import com.blogspot.soyamr.data.db.common.DbConverters.toEntity
import com.blogspot.soyamr.domain.common.DbTransactionProcessor
import com.blogspot.soyamr.domain.recipe.RecipesDbDataSource
import com.blogspot.soyamr.domain.recipe.model.Recipe
import com.blogspot.soyamr.domain.recipe.model.RecipeDetails
import com.blogspot.soyamr.domain.recipe.model.SortType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class RecipesDbDataSourceImpl @Inject constructor(
    private val dao: RecipeDao,
    private val dbTransactionProcessor: DbTransactionProcessor
) : RecipesDbDataSource {
    override fun observeRecipes(keyword: String, sortType: SortType): Flow<List<Recipe>> {
        return dao.observeRecipes(keyword, sortType).map { list -> list.map { it.toDomain() } }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeRecipeDetails(id: String): Flow<RecipeDetails?> {
        val recipeDetailsFlow = dao.observeRecipeDetails(id).distinctUntilChanged()
        val shortRecipesFlow = recipeDetailsFlow
            .map { it?.similar.orEmpty() }
            .flatMapLatest { ids ->
                dao.observeShortRecipes(ids).distinctUntilChanged()
            }
        return combine(recipeDetailsFlow, shortRecipesFlow) { details, shortRecipes ->
            details?.toDomain(shortRecipes)
        }
    }

    override suspend fun updateRecipes(recipes: List<Recipe>) {
        dao.insertRecipes(recipes.map { it.toEntity() })
    }

    override suspend fun updateRecipeDetails(recipeDetails: RecipeDetails) {
        dbTransactionProcessor.runInTransaction {
            dao.insertRecipeDetails(recipeDetails.toEntity())
            dao.insertShortRecipes(recipeDetails.similar.map { it.toEntity() })
        }
    }
}
