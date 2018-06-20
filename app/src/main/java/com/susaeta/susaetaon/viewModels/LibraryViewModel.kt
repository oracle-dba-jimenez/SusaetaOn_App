package com.susaeta.susaetaon.viewModels

import android.content.Context
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import com.susaeta.susaetaon.services.FileManager
import com.susaeta.susaetaon.services.SusaetaRepository
import com.susaeta.susaetaon.services.SusaetaRepositoryProvider
import com.susaeta.susaetaon.utils.ErrorMessage
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

    fun downloadServerFile(name: String): Call<ResponseBody> {
        return  repository.downloadPDFFromServer(name)
    }
}