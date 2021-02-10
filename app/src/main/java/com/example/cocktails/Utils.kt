package com.example.cocktails

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import coil.load
import com.example.cocktails.data.models.Drink

// Get ingredient names from the drink
fun Drink.getIngredientNames(): Set<String> =
    this.toString().split(" ")
            .filter { it.contains("ingredient") }
            .dropLast(1)
            .map {
                it.replace(",", "")
                it.split("=")[1]
            }
            .map { it.replace(",", "") }
            .filter { it != "null" }
            .toSortedSet()

fun ImageView.loadUrl(url: String?) {
    this.load(url){
        placeholder(R.drawable.loader)
        error(R.drawable.ic_image_failed)
    }
}

fun animatePropertyValuesHolder(views: List<View>, vararg values: PropertyValuesHolder): AnimatorSet{
    val animators = mutableListOf<Animator>()
    for (view in views) animators.add(ObjectAnimator.ofPropertyValuesHolder(view, *values).apply {
        duration = 300L
        interpolator = DecelerateInterpolator()
    })
    val animSet = AnimatorSet()
    animSet.playTogether(animators)
    return animSet
}