package com.blogspot.soyamr.recipes3.presentation.recipeList.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import com.blogspot.soyamr.recipes3.databinding.ItemRvRecipeBinding
import com.blogspot.soyamr.recipes3.presentation.common.recycler.GenericListAdapter
import com.blogspot.soyamr.recipes3.presentation.common.recycler.getItemCallBack
import com.blogspot.soyamr.recipes3.presentation.recipeList.models.RecipeUi

class RecipeAdapter(
    private val params: Params
) : GenericListAdapter<RecipeUi, RecipeViewHolder>(getItemCallBack()) {

    override fun createViewHolder(parent: ViewGroup): RecipeViewHolder {
        return RecipeViewHolder(
            params = params,
            binding = ItemRvRecipeBinding.inflate(
                /* inflater = */ LayoutInflater.from(parent.context),
                /* parent = */ parent,
                /* attachToParent = */ false
            )
        )
    }

    data class Params(
        val onItemClickListener: (RecipeUi) -> Unit,
    )
}