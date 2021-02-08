package com.example.cocktails.data.network

import com.example.cocktails.data.models.Drinks
import com.example.cocktails.data.models.Ingredients
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailDbEndpoints {

    @GET("lookup.php")
    suspend fun getDrink(@Query("i") key: Int): Drinks

    @GET("search.php")
    suspend fun getDrinks(@Query("f") key: String): Drinks

    @GET("search.php?")
    suspend fun searchDrink(@Query("s") key: String): Drinks

    @GET("lookup.php?")
    suspend fun lookupIngredient(@Query("iid") id: Int): Ingredients

    @GET("search.php?")
    suspend fun searchIngredient(@Query("i") name: String): Ingredients
}