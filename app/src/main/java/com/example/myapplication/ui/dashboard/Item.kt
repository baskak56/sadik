package com.example.myapplication.ui.dashboard

import TaskDecorator
import com.example.myapplication.R
import com.example.myapplication.ui.home.TaskInfo
import com.prolificinteractive.materialcalendarview.CalendarDay

data class Item(
    val imageResId: Int,
    val title: String,
    val calories: Int,
    var favorite: Boolean = false,
    val detailedDescription: String = "Описание не доступно",  // Дополнительное описание
    val detailedImageResId: Int = R.drawable.luk, // Дополнительное изображение
    val taskDates: Map<String, List<TaskInfo>> = emptyMap(),
    var decorator: TaskDecorator? = null
)