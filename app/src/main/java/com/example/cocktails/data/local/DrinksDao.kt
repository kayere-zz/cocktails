package com.example.cocktails.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cocktails.data.models.Drink
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDrink(drink: Drink)

    @Query("SELECT * FROM drinks_table")
    fun getDrinks(): Flow<List<Drink>>

    @Query("SELECT * FROM drinks_table WHERE drinkId LIKE :id")
    suspend fun getDrink(id: String): Drink

    @Query("SELECT COUNT(*) FROM drinks_table")
    suspend fun getCount(): Int

    @Query("SELECT * FROM drinks_table WHERE alcoholic LIKE :alcohol")
    fun filterDrinkByAlcohol(alcohol: String): Flow<List<Drink>>

    @Query("SELECT * FROM drinks_table WHERE category LIKE :category")
    fun filterDrinkByCategory(category:String): Flow<List<Drink>>
}