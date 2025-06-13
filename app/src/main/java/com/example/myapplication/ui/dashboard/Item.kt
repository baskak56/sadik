package com.example.myapplication.ui.dashboard

import TaskDecorator
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.ui.home.TaskInfo
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.example.myapplication.R


data class Item(
    val imageResId: Int,
    val title: String,
    val calories: Int,
    var favorite: Boolean = false,
    val detailedDescription: String = "Описание не доступно",
    val detailedImageResId: Int = R.drawable.luk,
    var taskDates: Map<String, List<TaskInfo>> = emptyMap(),
    var decorator: TaskDecorator? = null,
    val category: String = "",
    val plantingPeriod: String = "",
    val plantingMethod: String = "",
    val depth: Int = 0,
    val spacingBetween: Int = 0,
    val rowSpacing: Int = 0,
    val watering: String = "",
    val lighting: String = "",
    val fertilizer: String = "",
    val temperature: String = "",
    val maturity: String = "",
    val harvestTips: String = "",
    val compatibility: String = "",
    val diseases: String = "",
    val seedTreatment: String = "",
    val germinationTime: String = "",
    val transplantingTips: String = "",
    val growthFeatures: String = "",
    val pruning: String = "",
    val commonMistakes: String = "",
    val soilType: String = "",
    val pH: String = "",
    val cropRotation: String = "",
    val regionAdvice: String = ""
)
