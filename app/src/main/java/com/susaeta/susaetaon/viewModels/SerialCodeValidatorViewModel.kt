package com.susaeta.susaetaon.viewModels

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.susaeta.susaetaon.models.Book
import  com.susaeta.susaetaon.services.SusaetaRepositoryProvider

class SerialCodeValidatorViewModel {

    fun validateSerial(code: String, callback: (List<Book>) -> Unit) {
        val repository = SusaetaRepositoryProvider.provideSearchRepository()

        repository.getCollectionBooks(code).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    callback(result.items)
                }, { error ->
                   println("Invalid path : " + error.printStackTrace())
                })
    }
}