package com.susaeta.susaetaon.utils

import android.content.Context
import android.net.ConnectivityManager

class Utilities {
    companion object {
        fun getNameFileFrom(url: String): String {
            val clearFileName = url.split('/')
            val newFileName = clearFileName.get(clearFileName.count() - 1)
            return newFileName
        }

        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
            if (connectivityManager is ConnectivityManager) {
                val networkInfo = connectivityManager.activeNetworkInfo
                if (networkInfo != null) {
                    return networkInfo.isAvailable
                }
            }
            return false
        }
    }
}