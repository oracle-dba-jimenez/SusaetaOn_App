package com.susaeta.susaetaon.services

import android.content.Context
import com.google.common.io.Files
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File

class FileManager {
   companion object {
       fun saveFileOnDevice(context: Context, response: Response<ResponseBody>?) {
           println("File Directoty: " + context)
           val file = File(context.filesDir, "dummy.pdf")
           file.createNewFile()
           Files.asByteSink(file).write(response?.body()?.bytes())
       }
   }
}