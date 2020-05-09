package com.loginkt.data.support

import android.content.Context
import android.net.ConnectivityManager
import com.loginkt.R

class Connectivity {

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val isConnected =
            connectivityManager != null && connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
        if (!isConnected) {
            val title = context.resources.getString(R.string.information)
            val message = context.resources.getString(R.string.no_internet_connection)
            val btn = context.resources.getString(R.string.close)
            CustomDialog().alertDialog(context, title, message, btn, false)
        }
        return isConnected
    }
}