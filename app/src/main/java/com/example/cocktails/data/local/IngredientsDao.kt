package com.example.cocktails.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cocktails.data.models.Ingredient
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addIngredient(ingredient: Ingredient)

    @Query("SELECT * FROM ingredient_table")
    fun getIngredients(): Flow<List<Ingredient>>

    @Query("SELECT * FROM ingredient_table")
    suspend fun getHomeIngredients(): List<Ingredient>

    @Query("SELECT * FROM ingredient_table WHERE LOWER(name) LIKE :name")
    suspend fun getIngredientByName(name: String): Ingredient?
}