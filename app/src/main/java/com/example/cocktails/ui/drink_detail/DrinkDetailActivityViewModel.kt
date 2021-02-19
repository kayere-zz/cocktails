package com.example.cocktails.ui.drink_detail

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.cocktails.data.Repository
import com.example.cocktails.data.models.Drink
import com.example.cocktails.data.models.Ingredient
import com.example.cocktails.getIngredientNames
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class DrinkDetailActivityViewModel(private val repository: Repository, private val context: Context): ViewModel() {

    var drink: Drink? = null

    val fetchIngredients = flow<List<Ingredient>> {
        val ingredients = mutableSetOf<Ingredient>()
        val unAvailableIngredients = mutableListOf<String>()
        Log.d("TAG", ": ${drink?.getIngredientNames()} \n $drink")
        // fetching ingredients from the database using their names
        for (name in drink?.getIngredientNames()!!) {
            val ingredient = repository.getIngredientByName(name)
            if (ingredient != null) ingredients.add(ingredient)
            else unAvailableIngredients.add(name)
        }
        emit(ingredients.toList())
        for (unAvailableIngredient in unAvailableIngredients){
            var ingredient: Ingredient? = null
            try {
                ingredient = repository.searchIngredient(unAvailableIngredient).ingredients?.get(0)
            }
            catch (e: Exception){
                Toast.makeText(context, "Could not load all ingredients", Toast.LENGTH_SHORT).show()
            }
            ingredient?.let {
                it.thumb = "https://www.thecocktaildb.com/images/ingredients/${it.name}-medium.png"
                ingredients.add(it)
                emit(ingredients.toList())
                Log.d("TAG", "$unAvailableIngredient: $it")
                repository.addIngredient(it)
                delay(1000)
                Log.d("TAG", "$unAvailableIngredient: ${repository.getIngredientByName(unAvailableIngredient)}")
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