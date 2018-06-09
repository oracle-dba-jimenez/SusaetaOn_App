package com.susaeta.susaetaon.services

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.common.io.Files
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class FileManager {
   companion object {
       fun saveFileOnDevice(path: String, fileName: String, response: Response<ResponseBody>?) {
           val clearFileName = fileName.split('/')
           val newFileName = clearFileName.get(clearFileName.count()-1)

           val file = File(path, newFileName)

           file.createNewFile()
           //Files.asByteSink(file).write(response?.body()?.bytes())

           //For image
           val inputString = response?.body()?.byteStream()
           val bitmap = BitmapFactory.decodeStream(inputString)
           val outStream = FileOutputStream(file)
           bitmap.compress(Bitmap.CompressFormat.PNG, 0, outStream)
           Files.asByteSink(file).writeFrom(outStream.)
           println("File saved: $fileName")
       }
   }
}

//https://stackoverflow.com/questions/7769806/convert-bitmap-to-file/31746927
//https://stackoverflow.com/questions/35522341/retrofit-2-download-image-and-save-to-folder