package com.example.cocktails

import com.example.cocktails.data.models.Drink

// Get ingredient names from the drink
fun Drink.getIngredientNames(): Set<String> =
    this.toString().split(" ")
            .filter { it.contains("ingredient") }
            .dropLast(1)
            .map {
                it.replace(",", "")
                it.split("=")[1]
            }
            .map { it.replace(",", "") }
            .filter { it != "null" }
            .toSortedSet()