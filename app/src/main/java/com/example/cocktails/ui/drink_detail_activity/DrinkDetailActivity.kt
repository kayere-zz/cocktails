package com.example.cocktails.ui.drink_detail_activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.example.cocktails.R
import com.example.cocktails.data.Repository
import com.example.cocktails.data.local.DrinksDb
import com.example.cocktails.databinding.ActivityDrinkDetailBinding
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import kotlinx.coroutines.*

class DrinkDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: DrinkDetailActivityViewModel
    private lateinit var binding: ActivityDrinkDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        binding = ActivityDrinkDetailBinding.inflate(layoutInflater)
        window.sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 300L
            addTarget(binding.root)
        }
        window.sharedElementReturnTransition = MaterialContainerTransform().apply {
            addTarget(binding.root)
            duration = 300L
        }
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val db = DrinksDb.getDatabase(this)
        val repository = Repository(db.drinkDao(), db.ingredientsDao())

        viewModel = ViewModelProvider(this, DrinkDetailActivityViewModelFactory(repository, this))
            .get(DrinkDetailActivityViewModel::class.java)

        if (viewModel.drink == null) viewModel.drink = intent.getParcelableExtra("drink")

        binding.apply {
            drinkThumb.load(viewModel.drink?.drinkThumb){
                placeholder(R.drawable.loader)
            }
            drinkName.text = viewModel.drink?.drinkName
            drinkGlass.text = viewModel.drink?.glass
            instructionsLabel.alpha = 0F
            instructions.alpha = 0F
            ingredientsLabel.alpha = 0F
            ingredients.layoutManager = GridLayoutManager(this@DrinkDetailActivity, 3)
            instructions.text = viewModel.drink?.instructions
        }

        lifecycleScope.launch {
            delay(350L)
            val fade = PropertyValuesHolder.ofFloat(View.ALPHA, 0F, 1F)
            val moveUp = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 100F, 0F)
            val ingredientAnim = ObjectAnimator.ofPropertyValuesHolder(binding.ingredients, fade, moveUp).apply {
                duration = 300L
                interpolator = DecelerateInterpolator()
            }
            val ingredientLabelAnim = ObjectAnimator.ofPropertyValuesHolder(binding.ingredientsLabel, fade, moveUp).apply {
                duration = 300L
                interpolator = DecelerateInterpolator()
            }
            val instructionsLabelAnim = ObjectAnimator.ofPropertyValuesHolder(binding.instructionsLabel, fade, moveUp).apply {
                duration = 300L
                interpolator = DecelerateInterpolator()
            }
            val instructionsAnim = ObjectAnimator.ofPropertyValuesHolder(binding.instructions, fade, moveUp).apply {
                duration = 300L
                interpolator = DecelerateInterpolator()
            }
            val animSet = AnimatorSet()
            animSet.playTogether(ingredientAnim, ingredientLabelAnim, instructionsLabelAnim, instructionsAnim)

            var firstFetch = true
            viewModel.fetchIngredients.observe(this@DrinkDetailActivity, {
                if (firstFetch) {
                    firstFetch = false
                    when (it.size) {
                        0 -> {
                            binding.ingredientsLabel.text = getString(R.string.ingredient_absent)
                            animSet.start()
                        }
                        1 -> {
                            binding.ingredientsLabel.text = getString(R.string.ingredient)
                            binding.ingredients.adapter = SmallIngredientAdapter(it)
                            animSet.start()
                        }
                        else -> {
                            binding.ingredientsLabel.text = getString(R.string.ingredients)
                            binding.ingredients.adapter = SmallIngredientAdapter(it)
                            animSet.start()
                        }
                    }
                }
                else {
                    if (it.size > 1){
                        binding.ingredientsLabel.text = getString(R.string.ingredients)
                        binding.ingredients.adapter = SmallIngredientAdapter(it)
                    }
                }
            })
        }
    }
}