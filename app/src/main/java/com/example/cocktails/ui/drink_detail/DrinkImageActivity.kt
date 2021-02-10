package com.example.cocktails.ui.drink_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import coil.load
import com.example.cocktails.R
import com.example.cocktails.databinding.ActivityDrinkImageBinding
import com.example.cocktails.loadUrl

class DrinkImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDrinkImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.drinkThumb.loadUrl(intent.getStringExtra("imageUrl"))
    }
}