package com.kolecer.tawakal.templatengangau

import android.content.Context
import android.content.SharedPreferences

class Umum(c: Context) {
    private val sharedPreferences: SharedPreferences =
        c.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

    fun saveString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }
}