package com.susaeta.susaetaon.viewModels

import android.content.Context
import android.view.View
import android.widget.Button
import com.susaeta.susaetaon.models.Book
import com.susaeta.susaetaon.services.FileManager
import com.susaeta.susaetaon.services.SusaetaRepository
import com.susaeta.susaetaon.services.SusaetaRepositoryProvider
import okhttp3.ResponseBody
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class LibraryViewModel {
    private val repository: SusaetaRepository
    private val context: Context

    constructor(context: Context) {
        this.context = context
        this.repository = SusaetaRepositoryProvider.searchRepository()
    }

    fun downloadServerFile(name: String, downloadButton: Button, serialCode: String) {

        repository.downloadPDFFromServer(name).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                println("Download error.")
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                println("Downloading....")
                doAsync {
                    if (response!!.isSuccessful) {
                        FileManager.saveFileOnDevice(context.filesDir.path, name, response)
                        uiThread {
                            downloadButton.visibility = View.INVISIBLE
                            println("book name: $name, serialCode: $serialCode")
                            closeBook(bookName = name, serial = serialCode)
                        }
                    } else {
                        println("File not found.")
                    }
                }
            }
        })
    }

    fun getBooksOnLocalFileSystem(): List<Book> {
        val serializedBookLibrary = FileManager.readSerializedBookList(context)
        val bookList = arrayListOf<Book>()
        for (fileName in FileManager.findFileOnStorage(context).filter { book -> book.toLowerCase(Locale.ROOT).endsWith(".pdf")}) {
            for (book in serializedBookLibrary) {
                if (book.fileName == fileName) {
                    bookList.add(book)
                }
            }
        }

        return bookList
    }

    fun closeBook(bookName: String, serial: String) {
        println("Closing book...")
        val response = repository.closeBook(bookName.toLowerCase(Locale.ROOT).replace(".pdf", ""), serial).execute().body()
        println("Closing response: $response")
    }
}

