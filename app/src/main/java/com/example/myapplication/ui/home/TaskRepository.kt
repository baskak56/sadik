package com.example.myapplication.ui.home

import android.content.Context
import android.content.SharedPreferences
import com.example.myapplication.ui.home.TaskInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.prolificinteractive.materialcalendarview.CalendarDay

object TaskStorage {
    private val tasks = mutableMapOf<String, MutableList<TaskInfo>>()

    fun addTasks(day: CalendarDay, taskList: List<TaskInfo>) {
        val key = "${day.year}-${day.month}-${day.day}"
        tasks.getOrPut(key) { mutableListOf() }.addAll(taskList)
    }

    fun getTasksForDate(day: CalendarDay): List<TaskInfo> {
        val key = "${day.year}-${day.month}-${day.day}"
        return tasks[key] ?: emptyList()
    }

    fun clearTasks() {
        tasks.clear()
    }
}
