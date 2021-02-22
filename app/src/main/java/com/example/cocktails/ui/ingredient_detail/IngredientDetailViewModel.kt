package com.example.cocktails.ui.ingredient_detail

import android.util.Log
import androidx.lifecycle.*
import com.example.cocktails.data.Repository
import com.example.cocktails.data.models.Drink
import com.example.cocktails.data.models.Ingredient
import com.example.cocktails.getIngredientNames
import com.example.cocktails.ui.home.HomeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow

class IngredientDetailViewModel(private val repository: Repository): ViewModel(){

    val drinks = repository.getDrinksWithIngredient(IngredientDetailActivity().ingredient.name!!).asLiveData()
}

class IngredientDetailViewModelFactory(private val repository: Repository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IngredientDetailViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")

            return IngredientDetailViewModel(repository) as T
        }
        else
            throw IllegalArgumentException("Unknown ViewModel class")
    }

}