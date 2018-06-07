package com.susaeta.susaetaon.services

import com.susaeta.susaetaon.utils.GeneralConstants
import io.reactivex.Observable
import com.susaeta.susaetaon.models.Result
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface SusaetaApiService {

    @GET(GeneralConstants.COLLECTION_INFO + "PAOKM3HT00W11Y9")
    fun getCollectionBooks(): Observable<Result>

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
}
//PAOKM3HT00W11Y9