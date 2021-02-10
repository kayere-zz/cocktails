package com.example.cocktails.ui.ingredient_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.load
import com.example.cocktails.R
import com.example.cocktails.data.models.Ingredient
import com.example.cocktails.databinding.ActivityIngredientDetailBinding
import com.example.cocktails.loadUrl
import com.google.android.material.transition.platform.MaterialContainerTransform

class IngredientDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityIngredientDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val ingredient = intent.getParcelableExtra<Ingredient>("ingredient")

        binding.apply {
            ingredientName.text = ingredient?.name
            ingredientThumb.loadUrl(ingredient?.thumb)
            description.text = ingredient?.description
            ingredient?.description?.let {
                descriptionLabel.text = getString(R.string.description)
                ingredientType.text = ingredient.type
            }
        }
    }
}