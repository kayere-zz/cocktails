package com.example.cocktails.ui.drink_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails.data.models.Ingredient
import com.example.cocktails.databinding.SmallIngredientItemBinding
import com.example.cocktails.loadUrl

class SmallIngredientAdapter(var ingredients: List<Ingredient>) :
    RecyclerView.Adapter<SmallIngredientAdapter.SmallIngredientViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallIngredientViewHolder =
        SmallIngredientViewHolder(SmallIngredientItemBinding.inflate(LayoutInflater.from(parent.context)))


    override fun onBindViewHolder(holder: SmallIngredientViewHolder, position: Int) {
        holder.binding.apply {
            ingredientName.text = ingredients[position].name
            ingredientThumb.loadUrl(ingredients[position].thumb)
            ingredientCard.setOnClickListener {

            }
        }
    }

    override fun getItemCount(): Int = if (ingredients.size > 20) 20 else ingredients.size

    inner class SmallIngredientViewHolder(val binding: SmallIngredientItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}