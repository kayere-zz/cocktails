package com.example.cocktails.ui.drink_detail_activity

import android.content.Context
import android.util.Log
import android.widget.Toast
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

class DrinkDetailActivityViewModel(private val repository: Repository, private val context: Context): ViewModel() {

    var drink: Drink? = null

    val fetchIngredients = flow<List<Ingredient>> {
        val ingredients = ArrayList<Ingredient>()
        val unAvailableIngredients = ArrayList<String>()
        // getting names of ingredients form the drink
        val ingredientNames = drink.toString().split(" ")
                .filter { it.contains("ingredient") }.dropLast(1)
                .map {
                    it.replace(",", "")
                    it.split("=")[1]
                }
                .map { it.replace(",", "") }.toSortedSet()
        // fetching ingredients from the database using their names
        for (name in ingredientNames) {
            if (name != "null") {
                val ingredient = repository.getIngredientByName(name)
                if (ingredient != null) ingredients.add(ingredient)
                else unAvailableIngredients.add(name)
            }
        }
        emit(ingredients)
        for (name in unAvailableIngredients){
            try {
                val ingredient = repository.searchIngredient(name).ingredients?.get(0)
                ingredient?.let {
                    it.thumb = "https://www.thecocktaildb.com/images/ingredients/" +
                            it.name + "-medium.png"
                    ingredients.add(it)
                    emit(ingredients)
                    repository.addIngredient(it)
                    Log.d("TAG", ": ${repository.getIngredientByName(it.name!!)}")
                }
            }
            catch (e: Exception){
                Toast.makeText(context, "Could not load all ingredients", Toast.LENGTH_SHORT).show()
            }
        }
    }.asLiveData()

}

class DrinkDetailActivityViewModelFactory(private val repository: Repository, private val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DrinkDetailActivityViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return DrinkDetailActivityViewModel(repository, context) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }

}