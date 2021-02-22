package com.example.cocktails.ui.drink_detail

import android.animation.PropertyValuesHolder
import android.content.Context
import android.graphics.Color.TRANSPARENT
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocktails.R
import com.example.cocktails.animatePropertyValuesHolder
import com.example.cocktails.data.Repository
import com.example.cocktails.data.local.DrinksDb
import com.example.cocktails.databinding.FragmentDrinkDetailBinding
import com.example.cocktails.loadUrl
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class DrinkDetailFragment : Fragment() {
    private lateinit var binding: FragmentDrinkDetailBinding
    private lateinit var viewModel: DrinkDetailFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        requireActivity().window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        requireActivity().window.statusBarColor = TRANSPARENT
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            setPathMotion(MaterialArcMotion())
        }
        sharedElementReturnTransition = MaterialContainerTransform().apply {
            setPathMotion(MaterialArcMotion())
        }
        val db = DrinksDb.getDatabase(requireContext())
        val repository = Repository(db.drinkDao(), db.ingredientsDao())

        viewModel = ViewModelProvider(
            this, DrinkDetailFragmentViewModelFactory(
                repository,
                requireContext()
            )
        ).get(DrinkDetailFragmentViewModel::class.java)
        viewModel.drink = arguments?.getParcelable("Drink")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDrinkDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            instructionsLabel.alpha = 0F
            instructions.alpha = 0F
            ingredientsLabel.alpha = 0F
            drinkThumb.apply {
                loadUrl(viewModel.drink?.drinkThumb)
                setOnClickListener {

                }
            }
            drinkName.text = viewModel.drink?.drinkName
            drinkGlass.text = viewModel.drink?.glass
            ingredients.layoutManager = GridLayoutManager(requireContext(), 3)
        }

        lifecycleScope.launch {
            delay(400L)
            val fade = PropertyValuesHolder.ofFloat(View.ALPHA, 0F, 1F)
            val moveUp = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 100F, 0F)
            val animSet = animatePropertyValuesHolder(
                listOf(
                    binding.ingredients, binding.ingredientsLabel,
                    binding.instructionsLabel, binding.instructions
                ), fade, moveUp
            )
            animSet.start()

            var firstFetch = true
            viewModel.fetchIngredients.observe(requireActivity()) {
                if (firstFetch) {
                    firstFetch = false
                    when (it.size) {
                        0 -> {
                            binding.apply {
                                ingredientsLabel.text = getString(R.string.ingredient_absent)
                                instructionsLabel.text = getString(R.string.instructions)
                                instructions.text = viewModel.drink?.instructions
                            }
                            animSet.start()
                        }
                        1 -> {
                            binding.apply {
                                ingredientsLabel.text = getString(R.string.ingredient)
                                ingredients.adapter =
                                    SmallIngredientAdapter(it)
                                instructionsLabel.text = getString(R.string.instructions)
                                instructions.text = viewModel.drink?.instructions
                            }
                            animSet.start()
                        }
                        else -> {
                            binding.apply {
                                ingredientsLabel.text = getString(R.string.ingredients)
                                ingredients.adapter =
                                    SmallIngredientAdapter(it)
                                instructionsLabel.text = getString(R.string.instructions)
                                instructions.text = viewModel.drink?.instructions
                            }
                            animSet.start()
                        }
                    }
                } else {
                    if (it.size > 1) {
                        binding.ingredientsLabel.text = getString(R.string.ingredients)
                        binding.ingredients.adapter =
                            SmallIngredientAdapter(it)
                    }
                }
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        requireActivity().window.clearFlags(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }
}