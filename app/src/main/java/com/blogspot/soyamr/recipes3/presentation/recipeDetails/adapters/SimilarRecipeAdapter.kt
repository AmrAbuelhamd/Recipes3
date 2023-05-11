package com.blogspot.soyamr.recipes3.presentation.recipeDetails.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.blogspot.soyamr.recipes3.databinding.ItemRvSimilarRecipeBinding
import com.blogspot.soyamr.recipes3.presentation.common.recycler.GenericListAdapter
import com.blogspot.soyamr.recipes3.presentation.common.recycler.getItemCallBack
import com.blogspot.soyamr.recipes3.presentation.recipeDetails.models.ShortRecipeUi

class SimilarRecipeAdapter(
    private val params: Params
) : GenericListAdapter<ShortRecipeUi, SimilarRecipeViewHolder>(getItemCallBack()) {

    override fun createViewHolder(parent: ViewGroup): SimilarRecipeViewHolder {
        return SimilarRecipeViewHolder(
            params = params,
            binding = ItemRvSimilarRecipeBinding.inflate(
                /* inflater = */ LayoutInflater.from(parent.context),
                /* parent = */ parent,
                /* attachToParent = */ false
            )
        )
    }

    data class Params(
        val onItemClicked: (ShortRecipeUi) -> Unit
    )
}