package com.susaeta.susaetaon.services

import io.reactivex.Observable
import com.susaeta.susaetaon.models.Result

class SusaetaRepository(val apiService: SusaetaApiService) {

    fun getCollectionBooks() : Observable<Result> {
        return apiService.getCollectionBooks()
    }
}