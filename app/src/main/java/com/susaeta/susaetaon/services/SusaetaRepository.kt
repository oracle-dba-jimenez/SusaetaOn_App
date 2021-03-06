package com.susaeta.susaetaon.services

import com.susaeta.susaetaon.models.Result
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call

class SusaetaRepository(val apiService: SusaetaApiService) {

    fun getCollectionBooks(serial: String) : Observable<Result> {
        return apiService.getCollectionBooks(serial)
    }

    fun downloadPDFFromServer(fileName: String): Call<ResponseBody> {
        return apiService.downloadPDFFromServer(fileName)
    }

    fun closeBook(fileName: String, serial: String): Call<ResponseBody> {
        println("Closing the file send the request.")
        return apiService.closeBookAfterDownloaded(fileName, serial)
    }
}