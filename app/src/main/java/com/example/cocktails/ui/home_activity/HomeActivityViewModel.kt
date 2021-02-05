package com.example.cocktails.ui.home_activity

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.cocktails.data.Repository
import com.example.cocktails.data.models.Drink
import com.example.cocktails.data.models.Ingredient
import com.example.cocktails.work.Work

class HomeActivityViewModel(private val repository: Repository, private val context: Context): ViewModel() {

    val drinks: LiveData<List<Drink>> = repository.drinks().asLiveData()
    val alcoholDrinks: LiveData<List<Drink>> = repository.filterDrinkByAlcohol("Non Alcoholic").asLiveData()
    val nonAlcoholDrinks: LiveData<List<Drink>> = repository.filterDrinkByAlcohol("Non Alcoholic").asLiveData()
    val cocktails: LiveData<List<Drink>> = repository.filterDrinkByCategory("Cocktail").asLiveData()
    val ordinaryDrinks: LiveData<List<Drink>> = repository.filterDrinkByCategory("Ordinary Drink").asLiveData()
    val ingredients: LiveData<List<Ingredient>> = repository.getIngredients().asLiveData()

    suspend fun checkDb() {
        if (repository.getCount() < 400){
            val wm = WorkManager.getInstance(context)
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val work = OneTimeWorkRequest.Builder(Work::class.java)
                .setConstraints(constraints)
                .build()
            wm.enqueue(work)
        }
    }

}

class HomeActivityViewModelFactory(private val repository: Repository, private val context: Context):
    ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeActivityViewModel::class.java)){
            return HomeActivityViewModel(repository, context) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }

}