package com.example.cocktails.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cocktails.data.Repository
import com.example.cocktails.data.models.Drink
import com.example.cocktails.data.models.Ingredient

class HomeActivityViewModel(private val repository: Repository, private val context: Context): ViewModel() {

    suspend fun homeDrinks(): List<Drink> = repository.drinks()
    suspend fun alcoholDrinks(): List<Drink> = repository.getAlcoholicDrinks("Non Alcoholic")
    suspend fun nonAlcoholDrinks(): List<Drink> = repository.getAlcoholicDrinks("Non Alcoholic")
    suspend fun cocktails(): List<Drink> = repository.filterHomeDrinkByCategory("Cocktail")
    suspend fun ordinaryDrinks(): List<Drink> = repository.filterHomeDrinkByCategory("Ordinary Drink")
    suspend fun ingredients(): List<Ingredient> = repository.getHomeIngredients()

}

class HomeActivityViewModelFactory(private val repository: Repository, private val context: Context):
    ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeActivityViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return HomeActivityViewModel(repository, context) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }

}