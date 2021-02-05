package com.example.cocktails.ui.drink_detail_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.cocktails.data.Repository
import com.example.cocktails.data.models.Drink
import com.example.cocktails.data.models.Ingredient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class DrinkDetailActivityViewModel(private val repository: Repository): ViewModel() {

    var drink: Drink? = null

    val fetchIngredients = flow<List<Ingredient>> {
        val ingredients = ArrayList<Ingredient>()
        // getting names of ingredients form the drink
        val ingredientNames = drink.toString().split(" ")
            .filter { it.contains("ingredient") }
            .dropLast(1)
            .map {
                it.replace(",", "")
                it.split("=")[1]
            }
            .map { it.replace(",", "") }
        // fetching ingredients from the database using their names
        for (name in ingredientNames) {
            if (name != "null") {
                val ingredient = repository.getIngredientByName(name)
                ingredient?.let{
                    ingredients.add(ingredient)
                    emit(ingredients)
                }
            }
        }
    }.asLiveData()

}

class DrinkDetailActivityViewModelFactory(private val repository: Repository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DrinkDetailActivityViewModel::class.java)){
            return DrinkDetailActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }

}