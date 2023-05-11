package com.blogspot.soyamr.data.db.recipe

import androidx.room.*
import com.blogspot.soyamr.data.db.recipe.model.RecipeDetailsEntity
import com.blogspot.soyamr.data.db.recipe.model.RecipeEntity
import com.blogspot.soyamr.data.db.recipe.model.ShortRecipeEntity
import com.blogspot.soyamr.domain.recipe.model.SortType
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<RecipeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeDetails(recipe: RecipeDetailsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShortRecipes(recipes: List<ShortRecipeEntity>)

    @Query(
        "SELECT * FROM RecipeEntity " +
                "WHERE (name LIKE '%' || :query || '%') " +
                "OR (description LIKE '%' || :query || '%') " +
                "OR (instructions LIKE '%' || :query || '%') " +
                "ORDER BY CASE WHEN :sortType = 'NAME' THEN name END ASC, " +
                "CASE WHEN :sortType = 'LAST_UPDATE' THEN lastUpdated END DESC"
    )
    fun observeRecipes(query: String, sortType: SortType): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM RecipeDetailsEntity WHERE uuid = :id")
    fun observeRecipeDetails(id: String): Flow<RecipeDetailsEntity?>

    @Query("SELECT * FROM ShortRecipeEntity WHERE uuid IN (:ids)")
    fun observeShortRecipes(ids: List<String>): Flow<List<ShortRecipeEntity>>
}
