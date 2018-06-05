package models

import com.google.gson.annotations.SerializedName

data class Book (
        @SerializedName("articulo") val id: String,
        @SerializedName("nombre_archivo") var fileName: String,
        @SerializedName("nombre_portada") var thumbnailImageName: String,
        @SerializedName("fecha_vence") var expirationDate: String)

/**
 * Entire search result data class
 */
data class Result (
        val next: String,
        val items: List<Book>
)