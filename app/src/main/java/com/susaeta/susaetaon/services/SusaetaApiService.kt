package com.susaeta.susaetaon.services

import com.susaeta.susaetaon.models.Result
import com.susaeta.susaetaon.utils.GeneralConstants
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming
import java.security.SecureRandom
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import javax.security.cert.CertificateException
import javax.security.cert.X509Certificate

interface SusaetaApiService {

    companion object Factory {
        fun create(): SusaetaApiService {
            val okHttpClient = getUnsafeOkHttpClient() // OkHttpClient().newBuilder()
                    .build()

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(GeneralConstants.BASE_REMOTE_URL)
                    .client(okHttpClient)
                    .build()

            return retrofit.create(SusaetaApiService::class.java)
        }

        fun getUnsafeOkHttpClient(): OkHttpClient.Builder =
                try {
                    // Create a trust manager that does not validate certificate chains
                    val trustAllCerts: Array<TrustManager> = arrayOf(
                            object : X509TrustManager {
                                @Throws(CertificateException::class)
                                override fun checkClientTrusted(p0: Array<out java.security.cert.X509Certificate>?, authType: String?) = Unit

                                @Throws(CertificateException::class)
                                override fun checkServerTrusted(p0: Array<out java.security.cert.X509Certificate>?, authType: String?) = Unit

                                override fun getAcceptedIssuers(): Array<out java.security.cert.X509Certificate>? = arrayOf()
                            }
                    )
                    // Install the all-trusting trust manager
                    val sslContext: SSLContext = SSLContext.getInstance("SSL")
                    sslContext.init(null, trustAllCerts, SecureRandom())
                    // Create an ssl socket factory with our all-trusting manager
                    val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
                    val builder = OkHttpClient.Builder()
                    builder.sslSocketFactory(sslSocketFactory,
                            trustAllCerts[0] as X509TrustManager)
                    builder.hostnameVerifier { _, _ -> true }
                    builder
                } catch (e: Exception) {
                    throw RuntimeException(e)
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