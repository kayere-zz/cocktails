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
import com.example.cocktails.databinding.ActivityIngredientDetailBinding
import com.example.cocktails.loadUrl
import com.example.cocktails.ui.home.DrinksAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class IngredientDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityIngredientDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val db = DrinksDb.getDatabase(this)
        val repository = Repository(db.drinkDao(), db.ingredientsDao())
        val viewModel = ViewModelProvider(this, IngredientDetailViewModelFactory(repository))
                .get(IngredientDetailViewModel::class.java)
        if (viewModel.ingredient == null) viewModel.ingredient = intent.getParcelableExtra("ingredient")

        binding.apply {
            ingredientName.text = viewModel.ingredient?.name
            ingredientThumb.loadUrl(viewModel.ingredient?.thumb)
            description.text = viewModel.ingredient?.description
            drinksLabel.alpha = 0F
            description.alpha = 0F
            descriptionLabel.alpha = 0F
            viewModel.ingredient?.description?.let {
                descriptionLabel.text = getString(R.string.description)
                ingredientType.text = viewModel.ingredient?.type
            }
            drinks.layoutManager = GridLayoutManager(this@IngredientDetailActivity, 2)
            viewModel.drinks.observe(this@IngredientDetailActivity, {
                drinks.adapter = SmallDrinkAdapter(this@IngredientDetailActivity, it)
            })
        }

        lifecycleScope.launch(Dispatchers.Main){
            delay(350)
            val fade = PropertyValuesHolder.ofFloat(View.ALPHA, 0F, 1F)
            val moveUp = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 100F, 0F)
            val set = animatePropertyValuesHolder(listOf(binding.descriptionLabel, binding.description, binding.drinks, binding.drinksLabel), fade, moveUp)
            set.start()
        }
    }
}