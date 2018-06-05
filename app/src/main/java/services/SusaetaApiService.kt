package services

import Utils.GeneralConstants
import io.reactivex.Observable
import models.Result
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface SusaetaApiService {

    @GET("ords/susaetaon/archivos/coleccion/PAOKM3HT00W11Y9")
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