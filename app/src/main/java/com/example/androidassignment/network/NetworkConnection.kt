package com.example.androidassignment.network

import android.content.Context
import android.net.ConnectivityManager

object NetworkConnection {
    fun isNetworkConnected(context: Context): Boolean {
        val connectionManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectionManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
}