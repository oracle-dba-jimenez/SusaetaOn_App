package com.susaeta.susaetaon.services

import com.susaeta.susaetaon.models.Result
import com.susaeta.susaetaon.utils.GeneralConstants
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming

interface SusaetaApiService {

    companion object Factory {
        fun create(): SusaetaApiService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(GeneralConstants.BASE_REMOTE_URL)
                    .build()

            return retrofit.create(SusaetaApiService::class.java)
        }
    }

    // MARK: Application Programming Interface Methods
    @GET(GeneralConstants.COLLECTION_INFO + "{serialCode}")
    fun getCollectionBooks(@Path(value = "serialCode", encoded = true) serial: String): Observable<Result>

    @GET(GeneralConstants.BOOK_PATH + "{fileName}")
    @Streaming
    fun downloadPDFFromServer(@Path(value = "fileName", encoded = true) fileName: String): Call<ResponseBody>


    @GET(GeneralConstants.CLOSE_BOOK + "{fileName}" + ",{serial}")
    fun closeBookAfterDownloaded(@Path(value = "fileName", encoded = true) fileName: String,
                                 @Path(value = "serial", encoded = true) serial: String): Call<ResponseBody>
}