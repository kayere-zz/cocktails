package com.example.cocktails.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.cocktails.data.Repository
import com.example.cocktails.data.local.DrinksDb
import com.example.cocktails.data.models.Drinks
import com.example.cocktails.data.models.Ingredients
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import java.lang.Exception

class Work(private val context: Context, workerParameters: WorkerParameters)
    : CoroutineWorker(context, workerParameters) {

    private val db = DrinksDb.getDatabase(context)
    private val repository = Repository(db.drinkDao(), db.ingredientsDao())

    override suspend fun doWork(): Result {
        val drinksResult = GlobalScope.async { fetchDrinks() }
        val ingredientResult = GlobalScope.async { fetchIngredients() }

        return if (drinksResult.await() == Result.success() && ingredientResult.await() == Result.success())
                Result.success()
        else Result.failure()
    }

    private suspend fun fetchDrinks(): Result{
        ('a'..'z').map {
            try {
                val drinks = repository.getDrinks(it.toString()).drinks
                drinks?.let {
                    for (drink in drinks){
                        repository.addDrink(drink)
                    }
                }

            }
            catch (e: Exception){
                return Result.retry()
            }
        }
        return Result.success()
    }

    private suspend fun fetchIngredients(): Result{
        (1..1000).map {
            try {
                val ingredients = repository.lookupIngredient(it).ingredients
                ingredients?.let {
                    ingredients[0].thumb = "https://www.thecocktaildb.com/images/ingredients/${ingredients[0].name}-medium.png"
                    repository.addIngredient(ingredients[0])
                }
            }
            catch (e: Exception){
                return Result.retry()
            }
        }
        return Result.success()
    }

}

