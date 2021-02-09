package com.example.cocktails.ui.drink_detail

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
import com.example.cocktails.getIngredientNames
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class DrinkDetailActivityViewModel(private val repository: Repository, private val context: Context): ViewModel() {

    var drink: Drink? = null

    val fetchIngredients = flow<List<Ingredient>> {
        val ingredients = mutableListOf<Ingredient>()
        val unAvailableIngredients = mutableListOf<String>()
        Log.d("TAG", ": ${drink?.getIngredientNames()}")
        // fetching ingredients from the database using their names
        for (name in drink?.getIngredientNames()!!) {
            val ingredient = repository.getIngredientByName(name)
            if (ingredient != null) ingredients.add(ingredient)
            else unAvailableIngredients.add(name)
        }
        emit(ingredients)
        unAvailableIngredients.map { unAvailableIngredient ->
            viewModelScope.launch {
                try {
                    val ingredient = repository.searchIngredient(unAvailableIngredient).ingredients?.get(0)
                    ingredient?.let {
                        it.thumb = "https://www.thecocktaildb.com/images/ingredients/${it.name}-medium.png"
                        ingredients.add(it)
                        emit(ingredients)
                        val result = repository.addIngredient(it)
                        Log.d("TAG", ": $result")
                    }
                }
                catch (e: Exception){
                    Toast.makeText(context, "Could not load all ingredients", Toast.LENGTH_SHORT).show()
                }
            }
        }.joinAll()
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