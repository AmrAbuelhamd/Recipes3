package com.blogspot.soyamr.recipes3.presentation.recipeList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.blogspot.soyamr.domain.recipe.model.SortType
import com.blogspot.soyamr.recipes3.databinding.BottomSheetSortRecipesBinding
import com.blogspot.soyamr.recipes3.presentation.common.BaseBottomSheet

class SortRecipesBottomSheet : BaseBottomSheet<BottomSheetSortRecipesBinding>() {
    override val TAG: String = SortRecipesBottomSheet::class.java.simpleName

    private var callback: Callback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = parentFragment as? Callback
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): BottomSheetSortRecipesBinding {
        return BottomSheetSortRecipesBinding.inflate(inflater, container, false)
    }

    override fun BottomSheetSortRecipesBinding.setupViews() {
        sortByName.setOnClickListener {
            callback?.onSortTypeSelected(SortType.NAME)
            dismiss()
        }
        sortByDate.setOnClickListener {
            callback?.onSortTypeSelected(SortType.LAST_UPDATE)
            dismiss()
        }
    }

    fun interface Callback {
        fun onSortTypeSelected(sortType: SortType)
    }
}