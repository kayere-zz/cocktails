package com.example.cocktails.ui.ingredient_detail

import android.animation.PropertyValuesHolder
import android.graphics.Color
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocktails.R
import com.example.cocktails.animatePropertyValuesHolder
import com.example.cocktails.data.Repository
import com.example.cocktails.data.local.DrinksDb
import com.example.cocktails.databinding.FragmentIngredientDetailBinding
import com.example.cocktails.loadUrl
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class IngredientDetailFragment : Fragment() {
    private lateinit var binding: FragmentIngredientDetailBinding
    private lateinit var viewModel: IngredientDetailFragmentViewModel
    private val args: IngredientDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            scrimColor = Color.TRANSPARENT
            setPathMotion(MaterialArcMotion())
        }
        sharedElementReturnTransition = MaterialContainerTransform().apply {
            scrimColor = Color.TRANSPARENT
        }
        val db = DrinksDb.getDatabase(requireContext())
        val repository = Repository(db.drinkDao(), db.ingredientsDao())
        viewModel = ViewModelProvider(
            requireActivity(),
            IngredientDetailFragmentViewModelFactory(repository)
        )
            .get(IngredientDetailFragmentViewModel::class.java)
        viewModel.ingredient = args.ingredeint
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIngredientDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            ingredientName.text = viewModel.ingredient.name
            ingredientThumb.loadUrl(viewModel.ingredient.thumb)
            description.text = viewModel.ingredient.description
            drinks.alpha = 0F
            drinksLabel.alpha = 0F
            description.alpha = 0F
            descriptionLabel.alpha = 0F
            viewModel.ingredient.description?.let {
                descriptionLabel.text = getString(R.string.description)
                ingredientType.text = viewModel.ingredient.type
            }
            drinks.layoutManager = GridLayoutManager(requireContext(), 2)
        }

        viewModel.drinksWithIngredient().observe(requireActivity(), { drinkList ->
            binding.drinks.adapter = SmallDrinkAdapter(drinkList)
        })

        lifecycleScope.launch(Dispatchers.Main) {
            delay(350)
            val fade = PropertyValuesHolder.ofFloat(View.ALPHA, 0F, 1F)
            val moveUp = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 100F, 0F)
            val set = animatePropertyValuesHolder(
                listOf(
                    binding.descriptionLabel,
                    binding.description,
                    binding.drinks,
                    binding.drinksLabel
                ), fade, moveUp
            )
            set.start()
        }
    }
}