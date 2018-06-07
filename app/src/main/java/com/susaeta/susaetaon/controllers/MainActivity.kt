package com.susaeta.susaetaon.controllers

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.susaeta.susaetaon.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import services.SusaetaRepositoryProvider

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        goToLibraryButton.isEnabled = false

        validateCodeButton.setOnClickListener({
            val goToLibraryIntent = Intent(this@MainActivity, SerialCodeValidationActivity::class.java)
            startActivity(goToLibraryIntent)
        })

        goToLibraryButton.setOnClickListener({
            val goToLibraryIntent = Intent(this@MainActivity, LibraryCollectionActivity::class.java)
            startActivity(goToLibraryIntent)
        })
    }
}