package com.example.cocktails.data.models

import android.os.Parcel
import android.os.Parcelable
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
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(ingredientId)
        parcel.writeString(abv)
        parcel.writeString(alcohol)
        parcel.writeString(description)
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeString(thumb)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Ingredient> {
        override fun createFromParcel(parcel: Parcel): Ingredient {
            return Ingredient(parcel)
        }

        override fun newArray(size: Int): Array<Ingredient?> {
            return arrayOfNulls(size)
        }
    }
}