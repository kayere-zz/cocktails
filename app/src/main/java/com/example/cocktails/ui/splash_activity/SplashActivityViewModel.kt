package com.example.cocktails.ui.splash_activity

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.cocktails.data.Repository
import com.example.cocktails.work.Work
import java.lang.IllegalArgumentException

class SplashActivityViewModel(private val repository: Repository, private val context: Context): ViewModel() {

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

class SplashActivityViewModelFactory(private val repository: Repository, private val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashActivityViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return SplashActivityViewModel(repository, context) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }

}