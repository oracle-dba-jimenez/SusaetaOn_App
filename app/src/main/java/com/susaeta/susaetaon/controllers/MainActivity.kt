package com.susaeta.susaetaon.controllers

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.susaeta.susaetaon.R
import com.susaeta.susaetaon.utils.IntentPassIdentifiers
import com.susaeta.susaetaon.viewModels.LibraryViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val localBooks = LibraryViewModel(baseContext).getBooksOnLocalFileSystem()
        if (localBooks.isEmpty()) {
            goToLibraryButton.isEnabled = false
        }

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
}