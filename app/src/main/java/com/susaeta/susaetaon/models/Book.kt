package com.susaeta.susaetaon.models

import com.google.gson.annotations.SerializedName

data class Book (
        @SerializedName("articulo") val id: String,
        @SerializedName("nombre_portada") var thumbnailImageName: String,
        @SerializedName("nombre_archivo") var fileName: String,
        @SerializedName("fecha_vence") var expirationDate: String)

/**
 * Entire search result data class
 */
data class Result (
        val next: ObjectReference,
        val items: List<Book>
)

data class ObjectReference (
        val ref: String
)