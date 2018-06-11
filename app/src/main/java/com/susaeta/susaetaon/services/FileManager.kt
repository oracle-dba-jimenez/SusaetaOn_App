package com.susaeta.susaetaon.services

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.google.common.io.Files
import com.susaeta.susaetaon.utils.Utilities
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File

class FileManager {
   companion object {
       fun saveFileOnDevice(path: String, fileName: String, response: Response<ResponseBody>?, isImage: Boolean): String {
           val file = File(path, Utilities.getNameFileFrom(fileName))
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

           println("File saved -> ${file.absoluteFile}")
           return file.absoluteFile.path
       }

       fun getRelativeLocationPath(imageName: String) : Uri {
           return Uri.parse(imageName)
       }
   }
}