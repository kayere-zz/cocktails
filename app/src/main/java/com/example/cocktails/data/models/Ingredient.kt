package com.example.cocktails.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "ingredient_table")
data class Ingredient(
    @PrimaryKey
    @SerializedName("idIngredient")
    val ingredientId: String,
    @SerializedName("strABV")
    val abv: String?,
    @SerializedName("strAlcohol")
    val alcohol: String?,
    @SerializedName("strDescription")
    val description: String?,
    @SerializedName("strIngredient")
    val name: String?,
    @SerializedName("strType")
    val type: String?,
    var thumb: String?
)