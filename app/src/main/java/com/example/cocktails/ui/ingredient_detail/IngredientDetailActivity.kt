package com.example.cocktails.ui.ingredient_detail

import android.animation.PropertyValuesHolder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocktails.R
import com.example.cocktails.animatePropertyValuesHolder
import com.example.cocktails.data.Repository
import com.example.cocktails.data.local.DrinksDb
import com.example.cocktails.data.models.Ingredient
import com.example.cocktails.databinding.ActivityIngredientDetailBinding
import com.example.cocktails.loadUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class IngredientDetailActivity : AppCompatActivity() {
    lateinit var ingredient: Ingredient
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityIngredientDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ingredient = intent.getParcelableExtra("ingredient")!!
        val db = DrinksDb.getDatabase(this)
        val repository = Repository(db.drinkDao(), db.ingredientsDao())
        val viewModel = ViewModelProvider(this, IngredientDetailViewModelFactory(repository))
                .get(IngredientDetailViewModel::class.java)

        binding.apply {
            ingredientName.text = ingredient.name
            ingredientThumb.loadUrl(ingredient.thumb)
            description.text = ingredient.description
            drinksLabel.alpha = 0F
            description.alpha = 0F
            descriptionLabel.alpha = 0F
            ingredient.description?.let {
                descriptionLabel.text = getString(R.string.description)
                ingredientType.text = ingredient.type
            }
            drinks.layoutManager = GridLayoutManager(this@IngredientDetailActivity, 2)
        }

        viewModel.drinks.observe(this@IngredientDetailActivity, { drinkList ->
            binding.drinks.adapter = SmallDrinkAdapter(drinkList)
        })

        lifecycleScope.launch(Dispatchers.Main){
            delay(350)
            val fade = PropertyValuesHolder.ofFloat(View.ALPHA, 0F, 1F)
            val moveUp = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 100F, 0F)
            val set = animatePropertyValuesHolder(listOf(binding.descriptionLabel, binding.description, binding.drinks, binding.drinksLabel), fade, moveUp)
            set.start()
        }
    }
}