package com.example.androidassignment

import android.app.Application
import com.example.androidassignment.preferences.ModelPreferencesManager

class AndroidAssignmentApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ModelPreferencesManager.with(this)
    }
}