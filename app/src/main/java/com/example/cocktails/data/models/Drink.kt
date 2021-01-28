package com.example.cocktails.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "drinks_table")
data class Drink(
    @SerializedName("dateModified")
    val dateModified: String?,
    @PrimaryKey
    @SerializedName("idDrink")
    val drinkId: String,
    @SerializedName("strAlcoholic")
    val alcoholic: String?,
    @SerializedName("strCategory")
    val category: String?,
    @SerializedName("strCreativeCommonsConfirmed")
    val creativeCommonsConfirmed: String?,
    @SerializedName("strDrink")
    val drinkName: String?,
    @SerializedName("strDrinkAlternate")
    val drinkNameAlternate: String?,
    @SerializedName("strDrinkDE")
    val drinkNameDE: String?,
    @SerializedName("strDrinkES")
    val drinkNameES: String?,
    @SerializedName("strDrinkFR")
    val drinkNameFR: String?,
    @SerializedName("strDrinkThumb")
    val drinkImage: String?,
    @SerializedName("strDrinkZH-HANS")
    val strDrinkZHHANS: String?,
    @SerializedName("strDrinkZH-HANT")
    val strDrinkZHHANT: String?,
    @SerializedName("strGlass")
    val glass: String?,
    @SerializedName("strIBA")
    val strIBA: String?,
    @SerializedName("strImageAttribution")
    val imageAttribution: String?,
    @SerializedName("strImageSource")
    val imageSource: String?,
    @SerializedName("strIngredient1")
    val ingredient1: String?,
    @SerializedName("strIngredient2")
    val ingredient2: String?,
    @SerializedName("strIngredient3")
    val ingredient3: String?,
    @SerializedName("strIngredient4")
    val ingredient4: String?,
    @SerializedName("strIngredient5")
    val ingredient5: String?,
    @SerializedName("strIngredient6")
    val ingredient6: String?,
    @SerializedName("strIngredient7")
    val ingredient7: String?,
    @SerializedName("strIngredient8")
    val ingredient8: String?,
    @SerializedName("strIngredient9")
    val ingredient9: String?,
    @SerializedName("strIngredient10")
    val ingredient10: String?,
    @SerializedName("strIngredient11")
    val ingredient11: String?,
    @SerializedName("strIngredient12")
    val ingredient12: String?,
    @SerializedName("strIngredient13")
    val ingredient13: String?,
    @SerializedName("strIngredient14")
    val ingredient14: String?,
    @SerializedName("strIngredient15")
    val ingredient15: String?,
    @SerializedName("strInstructions")
    val instructions: String?,
    @SerializedName("strInstructionsDE")
    val instructionsDE: String?,
    @SerializedName("strInstructionsES")
    val instructionsES: String?,
    @SerializedName("strInstructionsFR")
    val instructionsFR: String?,
    @SerializedName("strInstructionsZH-HANS")
    val instructionsZHHANS: String?,
    @SerializedName("strInstructionsZH-HANT")
    val instructionsZHHANT: String?,
    @SerializedName("strMeasure1")
    val measure1: String?,
    @SerializedName("strMeasure10")
    val measure10: String?,
    @SerializedName("strMeasure11")
    val measure11: String?,
    @SerializedName("strMeasure12")
    val measure12: String?,
    @SerializedName("strMeasure13")
    val measure13: String?,
    @SerializedName("strMeasure14")
    val measure14: String?,
    @SerializedName("strMeasure15")
    val measure15: String?,
    @SerializedName("strMeasure2")
    val measure2: String?,
    @SerializedName("strMeasure3")
    val measure3: String?,
    @SerializedName("strMeasure4")
    val measure4: String?,
    @SerializedName("strMeasure5")
    val measure5: String?,
    @SerializedName("strMeasure6")
    val measure6: String?,
    @SerializedName("strMeasure7")
    val measure7: String?,
    @SerializedName("strMeasure8")
    val measure8: String?,
    @SerializedName("strMeasure9")
    val measure9: String?,
    @SerializedName("strTags")
    val tags: String?,
    @SerializedName("strVideo")
    val video: String?
)