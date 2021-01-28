package com.example.cocktails.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.cocktails.R
import com.example.cocktails.data.Repository
import com.example.cocktails.data.local.DrinksDb
import com.example.cocktails.data.models.Drinks
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = DrinksDb.getDatabase(this)
        val repo = Repository(db.drinkDao(), db.ingredientsDao())
        GlobalScope.launch(Dispatchers.Default) {
            (11000..200000).map {
                GlobalScope.launch {
                    try {
                        val drinks = repo.getDrinks(it).drinks
                        drinks?.let {
                            for (drink in it){
                                repo.addDrink(drink)
                            }
                        }
                    } catch (e: Exception){
                        Log.e("MainActivity", "onCreate: $e")
                    }
                }
            }
        }
        GlobalScope.launch {
            (1..1000).map {
                GlobalScope.launch {
                    try {
                        val ingredients = repo.lookupIngredient(it).ingredients
                        ingredients?.let { repo.addIngredient(it[0]) }
                    } catch (e: Exception){
                        Log.e("MainActivity", "onCreate: $e")
                    }
                }
            }
        }
        GlobalScope.launch {
            repo.drinks().collect {
                Log.d("MainActivity", "${it.size} Drinks")
            }
        }
        GlobalScope.launch {
            repo.getIngredients().collect{
                Log.d("MainActivity", "${it.size} Ingredients")
            }
        }
    }
}