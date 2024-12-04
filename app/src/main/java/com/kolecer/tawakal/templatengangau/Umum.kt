package com.kolecer.tawakal.templatengangau

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

class Umum(c: Context) {
    private val sharedPreferences: SharedPreferences =
        c.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

    fun saveString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun aturWarna(context: Context, warna: String) {
        when (warna) {
            "hejo" -> context.setTheme(R.style.Base_Theme_TemplateNgangau_Hejo)
            "biru" -> context.setTheme(R.style.Base_Theme_TemplateNgangau_Biru)
            "beureum" -> context.setTheme(R.style.Base_Theme_TemplateNgangau_Beureum)
            "koneng" -> context.setTheme(R.style.Base_Theme_TemplateNgangau_Koneng)
            "kayas" -> context.setTheme(R.style.Base_Theme_TemplateNgangau_Kayas)
            "bungur" -> context.setTheme(R.style.Base_Theme_TemplateNgangau_Bungur)
            "oren" -> context.setTheme(R.style.Base_Theme_TemplateNgangau_Oren)
            else -> context.setTheme(R.style.Base_Theme_TemplateNgangau_Hejo)
        }
    }

    fun temaGelap(tema: String) {
        if (tema == "on") {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}