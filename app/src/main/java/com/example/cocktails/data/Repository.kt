package com.example.cocktails.data

import com.example.cocktails.data.local.DrinksDao
import com.example.cocktails.data.local.IngredientsDao
import com.example.cocktails.data.models.Drink
import com.example.cocktails.data.models.Drinks
import com.example.cocktails.data.models.Ingredient
import com.example.cocktails.data.models.Ingredients
import com.example.cocktails.data.network.CocktailDbService
import kotlinx.coroutines.flow.Flow

class Repository(private val drinksDao: DrinksDao, private val ingredientsDao: IngredientsDao) {

    suspend fun getDrinks(key: Int): Drinks = CocktailDbService.api.getDrinks(key)

    suspend fun addDrink(drink: Drink) = drinksDao.addDrink(drink)

    fun drinks(): Flow<List<Drink>> = drinksDao.getDrinks()

    suspend fun searchDrink(key: String): Drinks = CocktailDbService.api.searchDrink(key)

    suspend fun lookupIngredient(id: Int): Ingredients = CocktailDbService.api.lookupIngredient(id)

    suspend fun addIngredient(ingredient: Ingredient) = ingredientsDao.addIngredient(ingredient)

    fun getIngredients(): Flow<List<Ingredient>> = ingredientsDao.getIngredients()
}