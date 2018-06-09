package com.susaeta.susaetaon.services

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.common.io.Files
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File

class FileManager {
   companion object {
       fun saveFileOnDevice(path: String, fileName: String, response: Response<ResponseBody>?, isImage: Bool) {
           val clearFileName = fileName.split('/')
           val newFileName = clearFileName.get(clearFileName.count()-1)

           val file = File(path, newFileName)
           file.createNewFile()

           if (isImage) { //For images
               val inputString = response?.body()?.byteStream()
               val bitmap = BitmapFactory.decodeStream(inputString)
               val outStream = ByteArrayOutputStream()
               bitmap.compress(Bitmap.CompressFormat.PNG, 0, outStream)

               Files.asByteSink(file).write(outStream.toByteArray())
           }else {
               Files.asByteSink(file).write(response?.body()?.bytes())
           }

           println("File saved -> $fileName")
       }
   }
}

//https://stackoverflow.com/questions/7769806/convert-bitmap-to-file/31746927
//https://stackoverflow.com/questions/35522341/retrofit-2-download-image-and-save-to-folder