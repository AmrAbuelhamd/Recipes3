package com.blogspot.soyamr.recipes3.presentation.recipeDetails

import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.badoo.mvicore.modelWatcher
import com.blogspot.soyamr.recipes3.R
import com.blogspot.soyamr.recipes3.databinding.FragmentRecipeDetailsBinding
import com.blogspot.soyamr.recipes3.presentation.common.BaseFragment
import com.blogspot.soyamr.recipes3.presentation.common.extentions.isLoading
import com.blogspot.soyamr.recipes3.presentation.common.extentions.showSnackBar
import com.blogspot.soyamr.recipes3.presentation.common.utils.DotPagerIndicatorDecoration
import com.blogspot.soyamr.recipes3.presentation.recipeDetails.adapters.ImagesAdapter
import com.blogspot.soyamr.recipes3.presentation.recipeDetails.adapters.SimilarRecipeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailsFragment : BaseFragment<FragmentRecipeDetailsBinding, RecipeDetailsUiState, RecipeDetailsEvent>() {
    override val viewModel: RecipeDetailsViewModel by viewModels()

    private var similarRecipeAdapter: SimilarRecipeAdapter? = null

    override val stateRenderer = modelWatcher {
        RecipeDetailsUiState::isLoading { isLoading ->
            binding.progressBar.isLoading = isLoading
        }
        RecipeDetailsUiState::similar { similar ->
            similarRecipeAdapter?.submitList(similar)
            binding.rvRecipes.isVisible = similar.isNotEmpty()
            binding.recommendedText.isInvisible = similar.isEmpty()
        }
        RecipeDetailsUiState::images { images ->
            binding.rvImages.adapter = ImagesAdapter(
                ImagesAdapter.Params(images = images)
            )
        }
        RecipeDetailsUiState::recipe binding@{ recipe ->
            recipe ?: return@binding
            with(binding) {
                nameTextView.text = recipe.name
                detailsDescriptionTextView.text = recipe.description
                dateTextView.text = recipe.lastUpdatedOnServer
                instructionTextView.text =
                    Html.fromHtml(recipe.instructions, Html.FROM_HTML_MODE_COMPACT)
            }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentRecipeDetailsBinding {
        return FragmentRecipeDetailsBinding.inflate(inflater, container, false)
    }

    override fun RecipeDetailsEvent.handleEvent() {
        when (this) {
            is RecipeDetailsEvent.ShareRecipe -> {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, getString(R.string.letsCookThis, recipeName))
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
        }
    }


    override fun FragmentRecipeDetailsBinding.setupViews() {
        toolbar.setupWithNavController(findNavController())
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.like) {
                showSnackBar(R.string.thanks)
            } else if (it.itemId == R.id.share) {
                viewModel.shareRecipe()
            }
            true
        }

        similarRecipeAdapter = SimilarRecipeAdapter(
            SimilarRecipeAdapter.Params { recipe ->
                findNavController().navigate(RecipeDetailsFragmentDirections.actionRecipeDetailsToSelf(recipe.id))
            }
        )
        rvRecipes.adapter = similarRecipeAdapter

        PagerSnapHelper().attachToRecyclerView(binding.rvImages)
        binding.rvImages.addItemDecoration(DotPagerIndicatorDecoration())
    }
}