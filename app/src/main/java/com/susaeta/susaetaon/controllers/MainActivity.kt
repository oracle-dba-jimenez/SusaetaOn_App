package com.susaeta.susaetaon.controllers

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

        var currentYear = Calendar.getInstance().get(Calendar.YEAR)
        currentYear = if (Calendar.getInstance().get(Calendar.MONTH) > 8) currentYear + 1 else  currentYear
        schoolarshipYear.text =  (currentYear - 1 ).toString() + " - " + currentYear

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
        // Verify exist a configuration file.
        val fileConfig = FileManager.findFileOnStorage(baseContext).filter { file ->
            file.contains(Calendar.getInstance().get(Calendar.YEAR).toString())
        }
        if (fileConfig.count() > 0) {
            if (Calendar.getInstance().get(Calendar.MONTH) > 8) { // If the current month is after August
                //Remove all books if is the next school year
                println("New schools year delete file: " + FileManager.removeAllFileFromStorage(baseContext))
                val file = File(baseContext.filesDir.path, Calendar.getInstance().get(Calendar.YEAR).toString() + ".config")
                file.createNewFile()
            }
        } else { // If no exist a configuration file create one to current year.
            val file = File(baseContext.filesDir.path,  Calendar.getInstance().get(Calendar.YEAR).toString() + ".config")
            file.createNewFile()
        }
    }
}