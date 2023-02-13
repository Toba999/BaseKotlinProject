package com.example.androiddevelopertask.local

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class HelperSharedPreferences @Inject constructor(private val context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("YourPreferencesFileName", Context.MODE_PRIVATE)
    }

    fun addData(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getData(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue)!!
    }

    fun deleteData(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
}