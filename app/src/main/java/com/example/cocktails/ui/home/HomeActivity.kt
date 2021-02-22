package com.example.cocktails.ui.home

import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.example.cocktails.R
import com.google.android.material.transition.platform.MaterialSharedAxis

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
            addTarget(R.id.home_layout)
            duration = 300L
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}