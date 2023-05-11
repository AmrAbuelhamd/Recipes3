package com.blogspot.soyamr.recipes3.presentation.recipeDetails.adapters;

import coil.load
import coil.transform.RoundedCornersTransformation
import com.blogspot.soyamr.recipes3.databinding.ItemRvSimilarRecipeBinding
import com.blogspot.soyamr.recipes3.presentation.common.recycler.BindableViewHolder
import com.blogspot.soyamr.recipes3.presentation.recipeDetails.models.ShortRecipeUi

class SimilarRecipeViewHolder(
    private val params: SimilarRecipeAdapter.Params,
    private val binding: ItemRvSimilarRecipeBinding
) : BindableViewHolder<ShortRecipeUi>(binding.root) {

    init {
        binding.root.setOnClickListener { params.onItemClicked(item) }
    }

    override fun ShortRecipeUi.bind() {
        binding.recipeName.text = name
        binding.recipePhoto.load(image) {
            transformations(RoundedCornersTransformation(32f))
        }
    }
}