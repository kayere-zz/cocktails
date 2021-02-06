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

        viewModel = ViewModelProvider(this, DrinkDetailActivityViewModelFactory(repository))
            .get(DrinkDetailActivityViewModel::class.java)

        if (viewModel.drink == null) viewModel.drink = intent.getParcelableExtra("drink")

        binding.apply {
            drinkThumb.load(viewModel.drink?.drinkThumb){
                placeholder(R.drawable.loader)
            }
            drinkName.text = viewModel.drink?.drinkName
            drinkGlass.text = viewModel.drink?.glass
            ingredients.layoutManager = GridLayoutManager(this@DrinkDetailActivity, 3)
            instructions.text = viewModel.drink?.instructions
        }

        lifecycleScope.launch {
            delay(400L)
            viewModel.fetchIngredients.observe(this@DrinkDetailActivity, {
                Log.d("TAG", "onCreate: ${it.size}")
                when (it.size) {
                    0 -> binding.ingredientsLabel.text = getString(R.string.ingredient_absent)
                    1 -> {
                        binding.ingredientsLabel.text = getString(R.string.ingredient)
                        binding.ingredients.adapter = SmallIngredientAdapter(it)
                    }
                    else -> {
                        binding.ingredientsLabel.text = getString(R.string.ingredients)
                        binding.ingredients.adapter = SmallIngredientAdapter(it)
                    }
                }
            })
        }
    }
}