package com.susaeta.susaetaon.utils

interface GeneralConstants {
    companion object {
        const val BASE_REMOTE_URL = "http://susaetaon.com:8080/"
        const val COLLECTION_INFO = "ords/susaetaon/archivos/coleccion/"
        const val BOOK_PATH = "libros/"
        const val THUMBNAIL_PATH = BASE_REMOTE_URL + "portadas/"
    }
}

interface IntentPassIdentifiers {
    companion object {
        const val BOOK_COLLECTION = "book_collection"
        const val PDF_FILE_PATH = "android_app_context"
        const val ENCRYPT_KEY   = "encrypt_key"
    }
}

interface ErrorMessage {
    companion object {
        const val INVALID_API_URL    = "Invalid path : "
        const val INVALID_CODE_ESP   = "El código proporcionado no es valido favor verificar."
    }
}