package com.blogspot.soyamr.recipes3.presentation.common.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.soyamr.recipes3.presentation.common.models.LongIdentity

abstract class BindableViewHolder<T : LongIdentity>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    lateinit var item: T
    fun onBind(item: T) {
        this.item = item
        item.bind()
    }

    protected abstract fun T.bind()
}