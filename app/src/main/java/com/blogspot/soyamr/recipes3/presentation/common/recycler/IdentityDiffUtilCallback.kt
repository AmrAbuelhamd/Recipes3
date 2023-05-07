package com.blogspot.soyamr.recipes3.presentation.common.recycler

import androidx.recyclerview.widget.DiffUtil
import com.blogspot.soyamr.recipes3.presentation.common.models.LongIdentity

inline fun <reified T : LongIdentity> getItemCallBack() = object : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}