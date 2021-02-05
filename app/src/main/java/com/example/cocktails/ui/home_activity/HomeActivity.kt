package com.example.cocktails.ui.home_activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cocktails.data.Repository
import com.example.cocktails.data.local.DrinksDb
import com.example.cocktails.databinding.ActivityHomeBinding
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementsUseOverlay = false
        super.onCreate(savedInstanceState)
        val binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DrinksDb.getDatabase(this)
        val repository = Repository(db.drinkDao(), db.ingredientsDao())
        val viewModel = ViewModelProvider(this, HomeActivityViewModelFactory(repository, this))
            .get(HomeActivityViewModel::class.java)
        GlobalScope.launch { viewModel.checkDb() }

        val drinksAdapter = DrinksAdapter(emptyList(), this)
        binding.drinks.adapter = drinksAdapter

        val cocktailsAdapter = DrinksAdapter(emptyList(), this)
        binding.cocktails.adapter = cocktailsAdapter

        val ordinaryDrinksAdapter = DrinksAdapter(emptyList(), this)
        binding.ordinaryDrinks.adapter = ordinaryDrinksAdapter

        val ingredientAdapter = IngredientAdapter(emptyList())
        binding.ingredients.adapter = ingredientAdapter

        viewModel.drinks.observe(this, {
            drinksAdapter.drinks = it
            drinksAdapter.notifyDataSetChanged()
            Log.d("Home", "onCreate: ${it.size}")
        })
        viewModel.cocktails.observe(this, {
            cocktailsAdapter.drinks = it
            cocktailsAdapter.notifyDataSetChanged()
        })
        viewModel.ordinaryDrinks.observe(this, {
            ordinaryDrinksAdapter.drinks = it
            ordinaryDrinksAdapter.notifyDataSetChanged()
        })
        viewModel.ingredients.observe(this, {
            ingredientAdapter.ingredients = it
            ingredientAdapter.notifyDataSetChanged()
        })
    }
}