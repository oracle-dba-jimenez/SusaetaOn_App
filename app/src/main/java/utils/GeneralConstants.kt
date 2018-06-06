package utils

interface GeneralConstants {
    companion object {
        const val BASE_REMOTE_URL = "http://susaetaon.com:8080/"
        const val COLLECTION_INFO = GeneralConstants.BASE_REMOTE_URL + "ords/susaetaon/archivos/coleccion/"
        const val THUMBNAIL_PATH = GeneralConstants.BASE_REMOTE_URL + "portadas/"
        const val BOOK_PATH = GeneralConstants.BASE_REMOTE_URL + "libros/"
    }
}
