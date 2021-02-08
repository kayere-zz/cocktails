package com.example.cocktails.ui.home_activity

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.ActivityOptions
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cocktails.R
import com.example.cocktails.data.models.Drink
import com.example.cocktails.databinding.DrinkItemBinding
import com.example.cocktails.ui.drink_detail_activity.DrinkDetailActivity

class DrinksAdapter(private var drinks: List<Drink>, private val activity: HomeActivity) : RecyclerView.Adapter<DrinksViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinksViewHolder =
        DrinksViewHolder(DrinkItemBinding.inflate(LayoutInflater.from(activity)))

    override fun onBindViewHolder(holder: DrinksViewHolder, position: Int) {
        holder.binding.apply {
            drinkCard.transitionName = "card$position to detail"
            drinkCard.setOnClickListener {
                val drinkDetailIntent = Intent(activity, DrinkDetailActivity::class.java)
                drinkDetailIntent.putExtra("drink", drinks[position])

                val options = ActivityOptions.makeSceneTransitionAnimation(
                        activity,
                        drinkCard,
                        "card to detail"
                )
                activity.startActivity(drinkDetailIntent, options.toBundle())
            }
            drinkName.text = drinks[position].drinkName
            drinkGlass.text = drinks[position].glass
            drinkThumb.load(drinks[position].drinkThumb){
                placeholder(R.drawable.loader)
                error(R.drawable.ic_image_failed)
            }
        }
    }

    override fun getItemCount(): Int = if (drinks.size > 20) 20 else drinks.size
}

class DrinksViewHolder(val binding: DrinkItemBinding) : RecyclerView.ViewHolder(binding.root)