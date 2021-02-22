package com.example.cocktails.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cocktails.data.Repository
import com.example.cocktails.data.models.Drink
import com.example.cocktails.data.models.Ingredient

class HomeActivityViewModel(private val repository: Repository, private val context: Context) :
    ViewModel() {

    suspend fun getHomeDrinks(): List<Drink> = repository.drinks()
    var homeDrinks: List<Drink>? = null
    suspend fun alcoholDrinks(): List<Drink> = repository.getAlcoholicDrinks("Non Alcoholic")
    suspend fun nonAlcoholDrinks(): List<Drink> = repository.getAlcoholicDrinks("Non Alcoholic")
    suspend fun getCocktails(): List<Drink> = repository.filterHomeDrinkByCategory("Cocktail")
    var cocktails: List<Drink>? = null
    suspend fun getOrdinaryDrinks(): List<Drink> =
        repository.filterHomeDrinkByCategory("Ordinary Drink")
    var ordinaryDrinks: List<Drink>? = null

    suspend fun getIngredients(): List<Ingredient> = repository.getHomeIngredients()
    var ingredients: List<Ingredient>? = null
}

class HomeActivityViewModelFactory(
    private val repository: Repository,
    private val context: Context
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeActivityViewModel(repository, context) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }

}