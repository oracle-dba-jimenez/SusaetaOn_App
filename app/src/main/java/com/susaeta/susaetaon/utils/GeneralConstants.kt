package com.susaeta.susaetaon.utils

interface GeneralConstants {
    companion object {
        const val BASE_REMOTE_URL = "http://susaetaon.com:8080/"
        const val COLLECTION_INFO = "ords/susaetaon/archivos/coleccion/"
        const val BOOK_PATH = "libros/"
    }
}

interface IntentPassIdentifiers {
    companion object {
        const val BOOK_COLLECTION = "book_collection"
        const val CONTEXT = "android_app_context"
    }
}

interface ErrorMessage {
    companion object {
        const val CANT_DOWNLOAD_FILE = "Cant' download file, please check."
        const val INVALID_API_URL    = "Invalid path : "
        const val INVALID_CODE_ESP   = "El código proporcionado no es valido favor verificar."
    }
}