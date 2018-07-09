package com.susaeta.susaetaon.viewModels

import android.content.Context
import com.susaeta.susaetaon.models.Book
import com.susaeta.susaetaon.services.SusaetaRepository
import com.susaeta.susaetaon.services.SusaetaRepositoryProvider
import com.susaeta.susaetaon.utils.ErrorMessage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SerialCodeValidatorViewModel {
    private val context: Context
    private val repository: SusaetaRepository

    constructor(context: Context) {
        this.context = context
        this.repository = SusaetaRepositoryProvider.searchRepository()
    }

    fun validateSerial(code: String, callback: (List<Book>) -> Unit) {
        getBookLibraryCollection(code, callback)
    }

    private fun getBookLibraryCollection(code: String, callback: (List<Book>) -> Unit) {
        repository.getCollectionBooks(code).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    callback(result.items)
                }, { error ->
                    println(ErrorMessage.INVALID_API_URL + error.printStackTrace())
                })
    }
}