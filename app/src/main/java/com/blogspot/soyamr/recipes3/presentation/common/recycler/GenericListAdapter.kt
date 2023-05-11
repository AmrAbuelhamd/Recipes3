package com.blogspot.soyamr.recipes3.presentation.common.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.blogspot.soyamr.recipes3.presentation.common.models.Identity

abstract class GenericListAdapter<T : Identity<*>, VH : BindableViewHolder<T>>(
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, VH>(diffCallback) {

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(getItem(position))
    }

    abstract fun createViewHolder(parent: ViewGroup): VH

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return createViewHolder(parent)
    }
}