package com.blogspot.soyamr.data.db.common

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.blogspot.soyamr.data.db.recipe.RecipeDao
import com.blogspot.soyamr.data.db.recipe.StringListConverter
import com.blogspot.soyamr.data.db.recipe.model.RecipeDetailsEntity
import com.blogspot.soyamr.data.db.recipe.model.RecipeEntity
import com.blogspot.soyamr.data.db.recipe.model.ShortRecipeEntity

@Database(
    version = 1,
    entities = [
        RecipeDetailsEntity::class,
        RecipeEntity::class,
        ShortRecipeEntity::class,
    ],
)
@TypeConverters(StringListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    public abstract fun recipeDao(): RecipeDao
}