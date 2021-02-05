package com.example.cocktails.ui.drink_detail_activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.example.cocktails.R
import com.example.cocktails.data.Repository
import com.example.cocktails.data.local.DrinksDb
import com.example.cocktails.data.models.Ingredient
import com.example.cocktails.databinding.ActivityDrinkDetailBinding
import com.example.cocktails.ui.home_activity.IngredientAdapter
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class DrinkDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        val binding = ActivityDrinkDetailBinding.inflate(layoutInflater)
        window.sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 300L
            addTarget(binding.root)
        }
        window.sharedElementReturnTransition = MaterialContainerTransform().apply {
            addTarget(binding.root)
            duration = 300L
        }
        setTheme(R.style.Theme_CocktailsTranslucentStatusBar)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val db = DrinksDb.getDatabase(this)
        val repository = Repository(db.drinkDao(), db.ingredientsDao())

        val viewModel = ViewModelProvider(this, DrinkDetailActivityViewModelFactory(repository))
            .get(DrinkDetailActivityViewModel::class.java)

        if (viewModel.drink == null) viewModel.drink = intent.getParcelableExtra("drink")

        binding.apply {
            drinkThumb.load(viewModel.drink?.drinkThumb)
            drinkName.text = viewModel.drink?.drinkName
            drinkGlass.text = viewModel.drink?.glass
            ingredients.layoutManager = GridLayoutManager(this@DrinkDetailActivity, 2)
        }

        viewModel.fetchIngredients.observe(this, {
            binding.ingredients.adapter = IngredientAdapter(it)
        })
    }
}