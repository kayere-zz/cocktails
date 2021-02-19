package com.example.cocktails.ui.ingredient_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.cocktails.data.Repository
import com.example.cocktails.data.models.Drink
import com.example.cocktails.data.models.Ingredient
import com.example.cocktails.getIngredientNames
import kotlinx.coroutines.flow.flow

class IngredientDetailViewModel(private val repository: Repository): ViewModel(){

    var ingredient: Ingredient? = null
    val drinks = flow<List<Drink>> {
        val drinksWithIngredient = mutableListOf<Drink>()
        val drinkList = repository.drinks()
        Log.d("TAG", ":${drinkList.size}")
        for (drink in drinkList) {
            if (drink.getIngredientNames().contains(ingredient?.name)){
                drinksWithIngredient.add(drink)
                emit(drinksWithIngredient)
            }
        }
    }.asLiveData()
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