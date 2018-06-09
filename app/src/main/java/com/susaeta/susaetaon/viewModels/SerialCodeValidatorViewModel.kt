package com.susaeta.susaetaon.viewModels

import android.content.Context
import com.susaeta.susaetaon.models.Book
import com.susaeta.susaetaon.services.FileManager
import com.susaeta.susaetaon.services.SusaetaRepository
import com.susaeta.susaetaon.services.SusaetaRepositoryProvider
import com.susaeta.susaetaon.utils.ErrorMessage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                    for (book: Book in result.items) {
                        downloadServerFile(book.thumbnailImageName, true)
                    }
                }, { error ->
                    println(ErrorMessage.INVALID_API_URL + error.printStackTrace())
                })
    }

    private fun downloadServerFile(name: String, isImage: Bool) {
        repository.downloadFileFromServer(name).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                //println("File donwloading ... $name")
                FileManager.saveFileOnDevice(context.filesDir.path, name, response, isImage)
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                println(ErrorMessage.CANT_DOWNLOAD_FILE)
            }
        })
    }
}