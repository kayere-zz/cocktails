package com.example.cocktails.ui.home

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cocktails.R
import com.example.cocktails.data.models.Ingredient
import com.example.cocktails.databinding.IngredientItemBinding
import com.example.cocktails.ui.ingredient_detail.IngredientDetailActivity

class IngredientAdapter(var ingredients: List<Ingredient>, private val activity: HomeActivity): RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder =
        IngredientViewHolder(IngredientItemBinding.inflate(LayoutInflater.from(parent.context)))


    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.binding.apply {
            root.transitionName = "Ingredient $position to detail"
            ingredientName.text = ingredients[position].name
            ingredientThumb.load(ingredients[position].thumb){
                placeholder(R.drawable.loader)
            }
            ingredientCard.setOnClickListener {
                val ingredientIntent = Intent(activity, IngredientDetailActivity::class.java)
                ingredientIntent.putExtra("ingredient", ingredients[position])
                val options = ActivityOptions.makeSceneTransitionAnimation(activity, root, "Ingredient to detail")
                activity.startActivity(ingredientIntent, options.toBundle())
            }
        }
    }

    override fun getItemCount(): Int = if (ingredients.size > 20) 20 else ingredients.size

    inner class IngredientViewHolder(val binding: IngredientItemBinding): RecyclerView.ViewHolder(binding.root)
}