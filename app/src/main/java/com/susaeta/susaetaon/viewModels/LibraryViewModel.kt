package com.susaeta.susaetaon.viewModels

import android.content.Context
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.susaeta.susaetaon.R.id.downloadButton
import com.susaeta.susaetaon.services.FileManager
import com.susaeta.susaetaon.services.SusaetaRepository
import com.susaeta.susaetaon.services.SusaetaRepositoryProvider
import com.susaeta.susaetaon.utils.ErrorMessage
import kotlinx.android.synthetic.main.fragment_item.view.*
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

    fun downloadServerFile(name: String, downloadButton: Button, progressBar: ProgressBar) {
        repository.downloadPDFFromServer(name).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                println("Download error.")
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                println("Download successful.")
                FileManager.saveFileOnDevice(context.filesDir.path, name, response)
                downloadButton.visibility = View.INVISIBLE
                progressBar.visibility = View.INVISIBLE
            }
        })
    }
}