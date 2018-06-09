package com.susaeta.susaetaon.viewModels

import android.content.Context
import com.susaeta.susaetaon.models.Book
import com.susaeta.susaetaon.services.FileManager
import com.susaeta.susaetaon.services.SusaetaRepository
import com.susaeta.susaetaon.services.SusaetaRepositoryProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SerialCodeValidatorViewModel {
    var context: Context

    constructor(context : Context) {
        this.context = context
    }

    fun validateSerial(code: String, callback: (List<Book>) -> Unit) {
        val repository = SusaetaRepositoryProvider.searchRepository()

        repository.getCollectionBooks(code).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    callback(result.items)
                }, { error ->
                    println("Invalid path : " + error.printStackTrace())
                })

        downloadServerFile(repository)
    }

    private fun downloadServerFile(repository: SusaetaRepository) {
        repository.downloadFileFromServer("pdf-sample.pdf").enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
               FileManager.saveFileOnDevice(context, response)
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                println("Cant' download file, please check.")
            }
        })
    }

}