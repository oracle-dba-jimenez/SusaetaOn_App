package com.susaeta.susaetaon.viewModels

import android.content.Context
import android.content.Intent
import com.susaeta.susaetaon.models.Book
import com.susaeta.susaetaon.services.FileManager
import com.susaeta.susaetaon.services.SusaetaRepository
import com.susaeta.susaetaon.services.SusaetaRepositoryProvider
import com.susaeta.susaetaon.utils.GeneralConstants
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LibraryViewModel {
    private val repository: SusaetaRepository
    private val context: Context

    constructor(context: Context) {
        this.context = context
        this.repository = SusaetaRepositoryProvider.searchRepository()
    }

    fun downloadServerFile(name: String) {

        repository.downloadPDFFromServer(name).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                println("Download error.")
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                println("Downloading....")
                FileManager.saveFileOnDevice(context.filesDir.path, name, response)
                context.sendBroadcast(Intent("Downloaded"))
            }
        })
    }

    fun getBooksOnLocalFileSystem(): List<Book> {
        val bookList = arrayListOf<Book>()
        for (fileName in FileManager.findFileOnStorage(context).filter { book -> book.endsWith(".pdf")}) {
            val thumbnailName = GeneralConstants.THUMBNAIL_PATH + fileName.replace(".pdf", ".png")
            bookList.add(Book("1234", thumbnailName, fileName, "", null))
        }

        return bookList
    }
}