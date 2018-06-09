package com.susaeta.susaetaon.controllers

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.susaeta.susaetaon.R
import com.susaeta.susaetaon.models.Book

class LibraryCollectionActivity : AppCompatActivity() {

    var listOfBooks = intent.extras.get("") as List<Book>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library_collection)

    }
}
