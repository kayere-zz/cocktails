package com.example.cocktails.ui.home_activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cocktails.data.models.Ingredient
import com.example.cocktails.databinding.IngredientItemBinding

class IngredientAdapter(var ingredients: List<Ingredient>): RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder =
        IngredientViewHolder(IngredientItemBinding.inflate(LayoutInflater.from(parent.context)))


    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.binding.apply {
            ingredientName.text = ingredients[position].name
            ingredientThumb.load(ingredients[position].thumb)
        }
    }

    override fun getItemCount(): Int = if (ingredients.size > 20) 20 else ingredients.size

    inner class IngredientViewHolder(val binding: IngredientItemBinding): RecyclerView.ViewHolder(binding.root)
}