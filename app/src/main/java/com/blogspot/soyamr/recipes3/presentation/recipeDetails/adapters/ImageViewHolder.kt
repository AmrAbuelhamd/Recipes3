package com.blogspot.soyamr.recipes3.presentation.recipeDetails.adapters

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.blogspot.soyamr.recipes3.databinding.ItemRvImageBinding

class ImageViewHolder(
    private val imageViewBinding: ItemRvImageBinding
) : RecyclerView.ViewHolder(imageViewBinding.root) {
    fun bind(imageLink: String) {
        imageViewBinding.root.load(imageLink)
    }
}