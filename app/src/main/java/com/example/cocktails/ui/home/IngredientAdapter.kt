package com.example.cocktails.ui.home

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.util.Pair
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cocktails.R
import com.example.cocktails.data.models.Ingredient
import com.example.cocktails.databinding.IngredientItemBinding
import com.example.cocktails.loadUrl
import com.example.cocktails.ui.ingredient_detail.IngredientDetailActivity

class IngredientAdapter(private val activity: HomeActivity, var ingredients: List<Ingredient>): RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder =
        IngredientViewHolder(IngredientItemBinding.inflate(LayoutInflater.from(parent.context)))


    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.binding.apply {
            ingredientName.text = ingredients[position].name
            ingredientThumb.loadUrl(ingredients[position].thumb)
            ingredientCard.setOnClickListener {
                val ingredientIntent = Intent(activity, IngredientDetailActivity::class.java)
                ingredientIntent.putExtra("ingredient", ingredients[position])
                val options = ActivityOptions.makeSceneTransitionAnimation(activity,
                        Pair(ingredientThumb, "Ingredient image"),
                        Pair(ingredientName, "Ingredient name"))
                activity.startActivity(ingredientIntent, options.toBundle())
            }
        }
    }

    override fun getItemCount(): Int = if (ingredients.size > 20) 20 else ingredients.size

    inner class IngredientViewHolder(val binding: IngredientItemBinding): RecyclerView.ViewHolder(binding.root)
}