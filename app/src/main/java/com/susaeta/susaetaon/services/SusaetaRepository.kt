package com.susaeta.susaetaon.services

import com.susaeta.susaetaon.models.Result
import io.reactivex.Observable

class SusaetaRepository(val apiService: SusaetaApiService) {

    fun getCollectionBooks(serial: String) : Observable<Result> {
        return apiService.getCollectionBooks(serial)
    }
}