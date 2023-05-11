package com.blogspot.soyamr.recipes3.presentation.recipeList.recycler;

import coil.load
import coil.transform.RoundedCornersTransformation
import com.blogspot.soyamr.recipes3.databinding.ItemRvRecipeBinding
import com.blogspot.soyamr.recipes3.presentation.common.recycler.BindableViewHolder
import com.blogspot.soyamr.recipes3.presentation.recipeList.models.RecipeUi

class RecipeViewHolder(
    private val params: RecipeAdapter.Params,
    private val binding: ItemRvRecipeBinding
) : BindableViewHolder<RecipeUi>(binding.root) {

    init {
        binding.root.setOnClickListener { params.onItemClickListener(item) }
    }

    override fun RecipeUi.bind() {
        with(binding) {
            recipeNameTextView.text = name
            descriptionTextView.text = description
            dateTextView.text = lastUpdated
            recipeImageView.layout(0, 0, 0, 0)
            recipeImageView.load(image) {
                this.transformations(RoundedCornersTransformation(topRight = 32f, bottomRight = 32f))
            }
        }
    }
}