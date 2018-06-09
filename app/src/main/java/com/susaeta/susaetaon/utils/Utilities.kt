package com.susaeta.susaetaon.utils

class Utilities {
    companion object {
        fun getNameFileFrom(url: String): String {
            val clearFileName = url.split('/')
            val newFileName = clearFileName.get(clearFileName.count() - 1)
            return newFileName
        }
    }
}