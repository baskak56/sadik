package com.example.myapplication.ui.dashboard

import com.example.myapplication.R

data class Item(
    val imageResId: Int,
    val title: String,
    val calories: Int,
    var favorite: Boolean = false,
    val detailedDescription: String = "Описание не доступно",  // Дополнительное описание
    val detailedImageResId: Int = R.drawable.luk // Дополнительное изображение
)