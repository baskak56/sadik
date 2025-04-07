package com.example.myapplication.ui.profile

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPrefs = application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    // User data
    private val _userName = MutableLiveData(getPref("name", "Неизвестно"))
    val userName: LiveData<String> = _userName

    private val _userEmail = MutableLiveData(getPref("email", "Неизвестно"))
    val userEmail: LiveData<String> = _userEmail

    private val _userStatus = MutableLiveData(getPref("user_status", "не выбран"))
    val userStatus: LiveData<String> = _userStatus

    fun saveStatus(status: String) {
        savePref("user_status", status)
        _userStatus.value = status
    }

    private fun getPref(key: String, defaultValue: String): String {
        return sharedPrefs.getString(key, defaultValue) ?: defaultValue
    }

    private fun savePref(key: String, value: String) {
        sharedPrefs.edit().putString(key, value).apply()
    }
}