package com.blogspot.soyamr.recipes3.presentation.recipeDetails.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.soyamr.recipes3.databinding.ItemRvImageBinding

class ImagesAdapter(
    private val params: Params
) : RecyclerView.Adapter<ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            imageViewBinding = ItemRvImageBinding.inflate(
                /* inflater = */ LayoutInflater.from(parent.context),
                /* parent = */ parent,
                /* attachToParent = */ false
            ),
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(params.images[position])
    }

    override fun getItemCount(): Int = params.images.size

    // TODO: add image full screen screen.
    data class Params(
        val images: List<String>,
    )
}