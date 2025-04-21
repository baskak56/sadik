package com.example.myapplication.ui.home

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.myapplication.ui.dashboard.Item

object StorageHelper {
    private const val PREF_NAME = "garden_prefs"
    private const val KEY_PLANTS = "planted_items"

    fun savePlants(context: Context, items: List<Item>) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = Gson().toJson(items)
        prefs.edit().putString(KEY_PLANTS, json).apply()
    }

    fun loadPlants(context: Context): List<Item> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_PLANTS, null)
        return if (json != null) {
            val type = object : TypeToken<List<Item>>() {}.type
            Gson().fromJson(json, type)
        } else {
            emptyList()
        }
    }
}