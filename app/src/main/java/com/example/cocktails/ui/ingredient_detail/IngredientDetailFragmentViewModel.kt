package com.example.cocktails.ui.ingredient_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.cocktails.data.Repository
import com.example.cocktails.data.models.Drink
import com.example.cocktails.data.models.Ingredient

class IngredientDetailFragmentViewModel(private val repository: Repository) : ViewModel() {

    lateinit var ingredient: Ingredient
    fun drinksWithIngredient(): LiveData<List<Drink>> =
        repository.getDrinksWithIngredient(ingredient.name!!).asLiveData()
}

class IngredientDetailFragmentViewModelFactory(private val repository: Repository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IngredientDetailFragmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return IngredientDetailFragmentViewModel(repository) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class")
    }

}