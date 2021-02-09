package com.example.cocktails.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.cocktails.data.Repository
import com.example.cocktails.data.local.DrinksDb
import com.example.cocktails.databinding.ActivityHomeBinding
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.google.android.material.transition.platform.MaterialSharedAxis
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityHomeBinding.inflate(layoutInflater)
        window.enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
            addTarget(binding.root)
        }
        window.allowEnterTransitionOverlap = true
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementsUseOverlay = false
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val db = DrinksDb.getDatabase(this)
        val repository = Repository(db.drinkDao(), db.ingredientsDao())
        val viewModel = ViewModelProvider(this, HomeActivityViewModelFactory(repository, this))
            .get(HomeActivityViewModel::class.java)

        lifecycleScope.launch {
            binding.apply {
                drinks.adapter = DrinksAdapter(viewModel.homeDrinks().shuffled(), this@HomeActivity)
                cocktails.adapter = DrinksAdapter(viewModel.cocktails().shuffled(), this@HomeActivity)
                ordinaryDrinks.adapter = DrinksAdapter(viewModel.ordinaryDrinks().shuffled(), this@HomeActivity)
                ingredients.adapter = IngredientAdapter(viewModel.ingredients().shuffled(), this@HomeActivity)
            }
        }
    }
}