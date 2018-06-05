package Utils

interface GeneralConstants {
    companion object {
        val BASE_REMOTE_URL = "http://susaetaon.com:8080/"
        val COLLECTION_INFO = GeneralConstants.BASE_REMOTE_URL + "ords/susaetaon/archivos/coleccion/"
        val THUMBNAIL_PATH = GeneralConstants.BASE_REMOTE_URL + "portadas/"
        val BOOK_PATH = GeneralConstants.BASE_REMOTE_URL + "libros/"
    }
}
