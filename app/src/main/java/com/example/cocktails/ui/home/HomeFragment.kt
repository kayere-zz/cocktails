package com.example.cocktails.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cocktails.R
import com.example.cocktails.data.Repository
import com.example.cocktails.data.local.DrinksDb
import com.example.cocktails.databinding.FragmentHomeBinding
import com.google.android.material.transition.Hold
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = Hold()
        val db = DrinksDb.getDatabase(this.requireContext())
        val repository = Repository(db.drinkDao(), db.ingredientsDao())
        viewModel =
            ViewModelProvider(this, HomeActivityViewModelFactory(repository, this.requireContext()))
                .get(HomeActivityViewModel::class.java)
        runBlocking {
            if (viewModel.homeDrinks == null) viewModel.homeDrinks = viewModel.getHomeDrinks().shuffled()
            if (viewModel.cocktails == null) viewModel.cocktails = viewModel.getCocktails().shuffled()
            if (viewModel.ordinaryDrinks == null) viewModel.ordinaryDrinks = viewModel.getOrdinaryDrinks().shuffled()
            if (viewModel.ingredients == null) viewModel.ingredients = viewModel.getIngredients().shuffled()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireContext().theme.applyStyle(R.style.Theme_Cocktails, true)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            binding.apply {
                drinks.adapter = DrinksAdapter(viewModel.homeDrinks!!, findNavController())
                cocktails.adapter = DrinksAdapter(viewModel.cocktails!!, findNavController())
                ordinaryDrinks.adapter =
                    DrinksAdapter(viewModel.ordinaryDrinks!!, findNavController())
                ingredients.adapter =
                    IngredientAdapter(HomeActivity(), viewModel.ingredients!!)
            }
        }
    }
}