package com.susaeta.susaetaon.controllers

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.appcompat.R.id.search_close_btn
import android.support.v7.widget.SearchView
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import com.susaeta.susaetaon.R
import com.susaeta.susaetaon.security.AESEncryptor
import com.susaeta.susaetaon.R.id.app_bar_search
import com.susaeta.susaetaon.R.string.search_hint
import org.apache.commons.io.FileUtils
import com.susaeta.susaetaon.utils.IntentPassIdentifiers
import kotlinx.android.synthetic.main.activity_document_viewer.*
import java.io.File

class DocumentViewerActivity : AppCompatActivity() {

    private lateinit var pdfUrlPath: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_viewer)
        setSupportActionBar(toolbar)
        val keyEncryptation = intent.extras.get(IntentPassIdentifiers.ENCRYPT_KEY) as String
        pdfUrlPath = intent.extras.get(IntentPassIdentifiers.PDF_FILE_PATH) as String
        println("Opening file...  $pdfUrlPath")

        val dataFree = AESEncryptor.decryptFile(keyEncryptation, File(pdfUrlPath))

        if (dataFree.isEmpty()) {
            println("File corrupted.!")
            File(pdfUrlPath).delete()
            startActivity(Intent(this@DocumentViewerActivity, LibraryCollectionActivity::class.java))
        } else {
            FileUtils.writeByteArrayToFile(File(pdfUrlPath.replace(".pdf", ".susaeta")), dataFree)
            pdfView.fromFile(File(pdfUrlPath.replace(".pdf", ".susaeta"))).enableSwipe(true)
                    .swipeHorizontal(true)
                    .spacing(10)
                    .defaultPage(0)
                    .enableAntialiasing(true)
                    .load()
        }

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
                    val goToPage = newText!!.toInt()
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