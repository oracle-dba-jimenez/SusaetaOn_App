package com.susaeta.susaetaon.services

import android.content.Context
import android.net.Uri
import com.google.common.io.Files
import com.susaeta.susaetaon.utils.Utilities
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File

class FileManager {
   companion object {
       fun saveFileOnDevice(path: String, fileName: String, response: Response<ResponseBody>?): String {
           val file = File(path, Utilities.getNameFileFrom(fileName))
           file.createNewFile()
           Files.asByteSink(file).write(response?.body()?.bytes())

           println("File saved -> ${file.absoluteFile}")
           return file.absoluteFile.path
       }

       fun findFileOnStorage(context: Context): List<String> {
           return context.filesDir.list().asList()
       }
   }
}