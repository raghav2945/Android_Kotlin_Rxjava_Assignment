package com.example.androidassignment.preferences

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder

object ModelPreferencesManager {
    private const val PREFERENCES_FILE_NAME = "ANDROID_ASSIGNMENT"
    lateinit var preferences: SharedPreferences

    fun with(application: Application) {
        preferences = application.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    }

    fun <T> put(`object`: T, key: String) {
        val jsonString = GsonBuilder().create().toJson(`object`)
        preferences.edit().putString(key, jsonString).apply()
    }

    inline fun <reified T> get(key: String): T? {
        val value = preferences.getString(key, null)
        return GsonBuilder().create().fromJson(value, T::class.java)
    }

    fun removeValue(KEY_NAME: String) {
        preferences.edit().remove(KEY_NAME).apply()
    }
}