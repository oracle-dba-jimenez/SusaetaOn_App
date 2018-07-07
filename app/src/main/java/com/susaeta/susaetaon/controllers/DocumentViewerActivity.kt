package com.susaeta.susaetaon.controllers

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ProgressBar
import com.susaeta.susaetaon.R
import com.susaeta.susaetaon.utils.IntentPassIdentifiers
import kotlinx.android.synthetic.main.activity_document_viewer.*
import kotlinx.android.synthetic.main.activity_serial_code_validator.*
import java.io.File

class DocumentViewerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_viewer)
        val pdfUrlPath = intent.extras.get(IntentPassIdentifiers.PDF_FILE_PATH) as String
        println("Opening file...  $pdfUrlPath")
        pdfView.fromFile(File(pdfUrlPath)).enableSwipe(true)
                .swipeHorizontal(true)
                .spacing(10)
                .enableAntialiasing(true)
                .load()
    }
}
///data/user/0/com.susaeta.susaetaon/files/978994512559.pdf