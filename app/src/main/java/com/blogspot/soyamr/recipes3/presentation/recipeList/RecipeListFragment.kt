package com.blogspot.soyamr.recipes3.presentation.recipeList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.badoo.mvicore.ModelWatcher
import com.badoo.mvicore.modelWatcher
import com.blogspot.soyamr.domain.recipe.model.SortType
import com.blogspot.soyamr.recipes3.R
import com.blogspot.soyamr.recipes3.databinding.FragmentRecipeListBinding
import com.blogspot.soyamr.recipes3.presentation.common.BaseFragment
import com.blogspot.soyamr.recipes3.presentation.common.extentions.isLoading
import com.blogspot.soyamr.recipes3.presentation.recipeList.models.RecipeUi
import com.blogspot.soyamr.recipes3.presentation.recipeList.recycler.RecipeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeListFragment :
    BaseFragment<FragmentRecipeListBinding, RecipeListState, RecipeListEvent>(),
    SortRecipesBottomSheet.Callback {

    override val viewModel: RecipeListViewModel by viewModels()
    private var adapter: RecipeAdapter? = null

    override val stateRenderer: ModelWatcher<RecipeListState> = modelWatcher {
        RecipeListState::recipes { recipes ->
            adapter?.submitList(recipes)
        }
        RecipeListState::isListLoading { isLoading ->
            binding.progressBar.isLoading = isLoading
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentRecipeListBinding {
        return FragmentRecipeListBinding.inflate(inflater, container, false)
    }

    override fun RecipeListEvent.handleEvent() = when (this) {
        is RecipeListEvent.HideSwipeLayoutLoading -> {
            binding.mySwipeToRefresh.isRefreshing = false
        }
    }

    override fun FragmentRecipeListBinding.setupViews() {
        val searchView = toolBar.findViewById<SearchView?>(R.id.searchBar)
        adapter = RecipeAdapter(RecipeAdapter.Params { item: RecipeUi ->
            searchView?.clearFocus()
            val action = RecipeListFragmentDirections.actionRecipeListToRecipeDetails(item.id)
            findNavController().navigate(action)
        })
        rvRecipes.adapter = adapter

        mySwipeToRefresh.setOnRefreshListener {
            viewModel.refreshData(isForceUpdate = true)
        }

        searchView?.queryHint = getString(R.string.search_hint)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.updateSearchQuery(newText?.trim())
                return true
            }
        })

        toolBar.setOnMenuItemClickListener {
            if (it.itemId == R.id.more) {
                SortRecipesBottomSheet().show(childFragmentManager)
            }
            true
        }
    }

    override fun onSortTypeSelected(sortType: SortType) {
        viewModel.updateSortType(sortType)
    }
}