package com.example.cocktails.ui.ingredient_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cocktails.data.Repository

class IngredientDetailViewModel(private val repository: Repository): ViewModel(){


}

class IngredientDetailViewModelFactory(private val repository: Repository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IngredientDetailViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")

            return IngredientDetailViewModel(repository) as T
        }
        else
            throw IllegalArgumentException("Unknown ViewModel class")
    }

}