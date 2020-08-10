package com.susaeta.susaetaon.utils

interface GeneralConstants {
    companion object {
        const val BASE_REMOTE_URL = "https://susaetaon.com:8181/"
        const val COLLECTION_INFO = "ords/susaetaon/archivos/coleccion/"
        const val CLOSE_BOOK = "ords/susaetaon/archivos/postear/"
        const val BOOK_PATH = "libros/"
    }
}

interface IntentPassIdentifiers {
    companion object {
        const val BOOK_COLLECTION = "book_collection"
        const val PDF_FILE_PATH = "android_app_context"
        const val ENCRYPT_KEY   = "encrypt_key"
        const val SERIAL_CODE = "serialCodeVariable"
    }
}

interface ErrorMessage {
    companion object {
        const val INVALID_API_URL    = "Invalid path : "
        const val INVALID_CODE_ESP   = "El código proporcionado no es valido favor verificar."
    }
}