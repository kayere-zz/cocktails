package com.example.cocktails.ui.drink_detail

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocktails.R
import com.example.cocktails.animatePropertyValueHolder
import com.example.cocktails.data.Repository
import com.example.cocktails.data.local.DrinksDb
import com.example.cocktails.databinding.ActivityDrinkDetailBinding
import com.example.cocktails.loadUrl
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
            drinkThumb.apply {
                loadUrl(viewModel.drink?.drinkThumb)
                setOnClickListener {
                    val imageIntent = Intent(this@DrinkDetailActivity, DrinkImageActivity::class.java)
                    imageIntent.putExtra("imageUrl", viewModel.drink!!.drinkThumb)
                    val options = ActivityOptions.makeSceneTransitionAnimation(this@DrinkDetailActivity,
                            this, getString(R.string.drink_thumb))
                    startActivity(imageIntent, options.toBundle())
                }
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
            val animSet = animatePropertyValuesHolder(listOf(binding.ingredients, binding.ingredientsLabel,
                binding.instructionsLabel, binding.instructions), fade, moveUp)
            animSet.start()

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
                            binding.ingredients.adapter = SmallIngredientAdapter(it, this@DrinkDetailActivity)
                            animSet.start()
                        }
                        else -> {
                            binding.ingredientsLabel.text = getString(R.string.ingredients)
                            binding.ingredients.adapter = SmallIngredientAdapter(it, this@DrinkDetailActivity)
                            animSet.start()
                        }
                    }
                }
                else {
                    if (it.size > 1){
                        binding.ingredientsLabel.text = getString(R.string.ingredients)
                        binding.ingredients.adapter = SmallIngredientAdapter(it, this@DrinkDetailActivity)
                    }
                }
            })
        }
    }
}