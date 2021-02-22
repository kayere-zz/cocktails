package com.example.cocktails.data.models

import com.google.gson.annotations.SerializedName


data class Ingredients(
    @SerializedName("ingredients")
    val ingredients: List<Ingredient>?
)