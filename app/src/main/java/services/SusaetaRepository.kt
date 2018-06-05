package services

import io.reactivex.Observable
import models.Result

class SusaetaRepository(val apiService: SusaetaApiService) {

    fun getCollectionBooks() : Observable<Result> {
        return apiService.getCollectionBooks()
    }
}