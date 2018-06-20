package com.susaeta.susaetaon.controllers

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.susaeta.susaetaon.R
import com.susaeta.susaetaon.utils.IntentPassIdentifiers
import kotlinx.android.synthetic.main.activity_document_viewer.*
import java.io.File

class DocumentViewerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_viewer)
        val pdfUrlPath = intent.extras.get(IntentPassIdentifiers.PDF_FILE_PATH) as String
        pdfView.fromFile(File(pdfUrlPath)).enableSwipe(true)
                .swipeHorizontal(true)
                .spacing(10)
                .enableAntialiasing(true)
                .load()
    }
}
