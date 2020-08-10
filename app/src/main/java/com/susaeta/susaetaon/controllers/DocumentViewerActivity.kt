package com.susaeta.susaetaon.controllers

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.R.id.search_close_btn
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.susaeta.susaetaon.R
import com.susaeta.susaetaon.R.id.app_bar_search
import com.susaeta.susaetaon.R.string.search_hint
import com.susaeta.susaetaon.security.AESEncryptor
import com.susaeta.susaetaon.utils.IntentPassIdentifiers
import kotlinx.android.synthetic.main.activity_document_viewer.*
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.util.*

class DocumentViewerActivity : AppCompatActivity() {

    private lateinit var pdfUrlPath: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_viewer)
        setSupportActionBar(toolbar)
        val keyEncryptation = intent.extras?.get(IntentPassIdentifiers.ENCRYPT_KEY) as String
        pdfUrlPath = intent.extras?.get(IntentPassIdentifiers.PDF_FILE_PATH) as String
        println("Opening file...  $pdfUrlPath")

        val dataFree = AESEncryptor.decryptFile(keyEncryptation, File(pdfUrlPath))

        if (dataFree.isEmpty()) {
            println("File corrupted.!")
            File(pdfUrlPath).delete()
            startActivity(Intent(this@DocumentViewerActivity, LibraryCollectionActivity::class.java))
        } else {
            try {
                FileUtils.writeByteArrayToFile(File(pdfUrlPath.toLowerCase(Locale.ROOT).replace(".pdf", ".susaeta")), dataFree)
                pdfView.fromFile(File(pdfUrlPath.toLowerCase(Locale.ROOT).replace(".pdf", ".susaeta"))).enableSwipe(true)
                        .swipeHorizontal(true)
                        .spacing(10)
                        .defaultPage(0)
                        .enableAntialiasing(true)
                        .load()
            } catch(e: IOException ) {
                Log.d("Exception ", e.message.toString());
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        File(pdfUrlPath.toLowerCase(Locale.ROOT).replace(".pdf", ".susaeta")).delete()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView= menu.findItem(R.id.app_bar_search).actionView as SearchView

        val searchEditText = searchView.findViewById(R.id.search_src_text) as EditText
        searchEditText.setTextColor(Color.WHITE)
        searchEditText.setHintTextColor(Color.WHITE)
        searchEditText.inputType = InputType.TYPE_CLASS_NUMBER
        searchEditText.hint = getString(search_hint)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return onQueryTextChange(query)
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    val goToPage = newText.toInt()
                    return if (pdfView.pageCount >= goToPage) {
                        pdfView.jumpTo(goToPage, true)
                        true
                    }else {
                        pdfView.jumpTo(0, true)
                        true
                    }
                }
                return false
            }
        })

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        val menuItem = menu.findItem(app_bar_search)

        menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                return true
            }

            //Clear when press back button on search view.
            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                searchEditText.text.clear()
                return true
            }
        })

        // Clear when press close / cancel query on search view.
        val closeButton = searchView.findViewById<ImageView>(search_close_btn)
        closeButton.setOnClickListener {
            searchEditText.text.clear()
        }
        return true
    }
}