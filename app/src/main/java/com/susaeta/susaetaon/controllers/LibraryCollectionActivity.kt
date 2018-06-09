package com.susaeta.susaetaon.controllers

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.susaeta.susaetaon.R
import com.susaeta.susaetaon.models.Book
import com.susaeta.susaetaon.utils.IntentPassIdentifiers

class LibraryCollectionActivity : AppCompatActivity() {

  //  val listOfBooks = intent.extras.get(IntentPassIdentifiers.BOOK_COLLECTION) as List<Book>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library_collection)

    }
}
