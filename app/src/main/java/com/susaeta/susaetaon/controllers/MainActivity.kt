package com.susaeta.susaetaon.controllers

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.susaeta.susaetaon.R
import com.susaeta.susaetaon.models.Book
import com.susaeta.susaetaon.services.FileManager
import com.susaeta.susaetaon.utils.IntentPassIdentifiers
import com.susaeta.susaetaon.viewModels.LibraryViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.Serializable
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var localBooks: List<Book>

    override fun onResume() {
        super.onResume()
        localBooks = LibraryViewModel(baseContext).getBooksOnLocalFileSystem()
        goToLibraryButton.isEnabled = !localBooks.isEmpty()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        verifyYearSchoolForRemoveOldBooks()

        @SuppressLint("SetTextI18n")
        schoolarshipYear.text = (schoolYear()-1).toString() + " - " + schoolYear().toString()

        validateCodeButton.setOnClickListener {
            val goToLibraryIntent = Intent(this@MainActivity, SerialCodeValidationActivity::class.java)
            startActivity(goToLibraryIntent)
        }

        goToLibraryButton.setOnClickListener {
            val goToLibraryIntent = Intent(this@MainActivity, LibraryCollectionActivity::class.java)
            goToLibraryIntent.putExtra(IntentPassIdentifiers.BOOK_COLLECTION, localBooks as Serializable)
            startActivity(goToLibraryIntent)
        }
    }

    private fun verifyYearSchoolForRemoveOldBooks() {
        println(FileManager.findFileOnStorage(baseContext))

        // Verify exist a configuration file.
        val fileConfig = FileManager.findFileOnStorage(baseContext).filter { file ->
            file.contains(schoolYear().toString() + ".config")
        }

        if (fileConfig.count() ==  0) {
            if (Calendar.getInstance().get(Calendar.MONTH) + 1 >= 8) { // If the current month is after August
                //Remove all books if is the next school year
                println("New schools year delete file: " + FileManager.removeAllFileFromStorage(baseContext))
                val file = File(baseContext.filesDir.path, schoolYear().toString() + ".config")
                file.createNewFile()
            }
        }
    }

    private fun schoolYear(): Int {
        var currentYear = Calendar.getInstance().get(Calendar.YEAR)
        if (Calendar.getInstance().get(Calendar.MONTH)+ 1 >= 8){
            currentYear += 1
        }
        return currentYear
    }
}