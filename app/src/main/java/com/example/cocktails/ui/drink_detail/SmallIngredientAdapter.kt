package com.example.cocktails.ui.drink_detail

import android.app.ActivityOptions
import android.content.Intent
import android.util.Pair
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cocktails.R
import com.example.cocktails.data.models.Ingredient
import com.example.cocktails.databinding.SmallIngredientItemBinding
import com.example.cocktails.loadUrl
import com.example.cocktails.ui.ingredient_detail.IngredientDetailActivity

class SmallIngredientAdapter(var ingredients: List<Ingredient>, private val activity: DrinkDetailActivity): RecyclerView.Adapter<SmallIngredientAdapter.SmallIngredientViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallIngredientViewHolder =
        SmallIngredientViewHolder(SmallIngredientItemBinding.inflate(LayoutInflater.from(parent.context)))


    override fun onBindViewHolder(holder: SmallIngredientViewHolder, position: Int) {
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

    inner class SmallIngredientViewHolder(val binding: SmallIngredientItemBinding): RecyclerView.ViewHolder(binding.root)
}