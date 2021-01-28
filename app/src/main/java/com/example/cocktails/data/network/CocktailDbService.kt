package com.example.cocktails.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CocktailDbService {
    private const val baseUri = "https://www.thecocktaildb.com/api/json/v1/1/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUri)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: CocktailDbEndpoints by lazy {
        retrofit.create(CocktailDbEndpoints::class.java)
    }
}