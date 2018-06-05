package services

import Utils.GeneralConstants
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import models.Book
import okhttp3.*
import java.io.IOException

//PAOKM3HT00W11Y9
class SusaetaOnServiceManager {

    private val client = OkHttpClient()

    fun fetchJsonBooksCollections(serial: String) : Array<Book>? {
        println("Attempting to fetch JSON")
        val JSONResponse = fetchJsonResponse(GeneralConstants.COLLECTION_INFO + serial)
        println("Response:: " + JSONResponse?.body()?.string())

        val gson = GsonBuilder().create()
        val collectionBooks = gson.fromJson(JSONResponse?.body()?.string(), ResponseObjects::class.java)

        return null
    }

    private fun fetchJsonResponse(urlRequest: String) : Response? {
        val request = Request.Builder().url(urlRequest).build()
        var responseObj = client.newCall(request).execute()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call?, response: Response?) {
                println("Response:: "+ response?.body()?.string())
                responseObj = response
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("ERROR - Failed to execute request: "+e.toString())
            }
        })

        return responseObj
    }
}

class ResponseObjects(val ref: String? = null, @SerializedName("items")val items: List<Book>)
