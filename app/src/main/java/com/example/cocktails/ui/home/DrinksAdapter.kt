package com.example.cocktails.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails.R
import com.example.cocktails.data.models.Drink
import com.example.cocktails.databinding.DrinkItemBinding
import com.example.cocktails.loadUrl

class DrinksAdapter(private var drinks: List<Drink>, private val navController: NavController) :
    RecyclerView.Adapter<DrinksViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinksViewHolder =
        DrinksViewHolder(DrinkItemBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: DrinksViewHolder, position: Int) {
        holder.binding.apply {
            root.transitionName = "card $position to detail"
            root.setOnClickListener {
                val bundle = bundleOf("Drink" to drinks[position])
                val extras = FragmentNavigatorExtras(root to "detail page")
                navController.navigate(R.id.action_homeFragment_to_drinkDetailFragment, bundle, null, extras)
            }
            drinkName.text = drinks[position].drinkName
            drinkGlass.text = drinks[position].glass
            drinkThumb.loadUrl(drinks[position].drinkThumb)
        }
    }

    override fun getItemCount(): Int = if (drinks.size > 20) 20 else drinks.size
}

class DrinksViewHolder(val binding: DrinkItemBinding) : RecyclerView.ViewHolder(binding.root)