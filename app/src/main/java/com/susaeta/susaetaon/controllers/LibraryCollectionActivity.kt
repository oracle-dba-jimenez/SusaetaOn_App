package com.susaeta.susaetaon.controllers

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.susaeta.susaetaon.R
import com.susaeta.susaetaon.models.Book
import com.susaeta.susaetaon.utils.IntentPassIdentifiers
import com.susaeta.susaetaon.viewModels.BookLibraryRecyclerViewAdapter
import com.susaeta.susaetaon.viewModels.LibraryViewModel
import kotlinx.android.synthetic.main.activity_library_collection.*
import kotlinx.android.synthetic.main.fragment_item.*
import kotlinx.android.synthetic.main.fragment_item.view.*

class LibraryCollectionActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    lateinit var listOfBooks: List<Book>
    lateinit var viewModel: LibraryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library_collection)

        viewModel = LibraryViewModel(baseContext)
        listOfBooks = intent.extras.get(IntentPassIdentifiers.BOOK_COLLECTION) as List<Book>

        var spanCountColumns = 3
        if (resources.configuration.orientation  == Configuration.ORIENTATION_LANDSCAPE) {
            spanCountColumns = 4
        }

        val gridLayout = GridLayoutManager(baseContext, spanCountColumns)
        gridLayout.paddingRight.and(40)
        viewManager = gridLayout
        viewAdapter = BookLibraryRecyclerViewAdapter(listOfBooks, null, baseContext)

        recyclerView = my_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        //TODO: Intentar sacar este listener de aqui y delegar esta responsabilidad.
        recyclerView.addOnItemTouchListener(LibraryRecycleTouchListener(baseContext, object: ClickListener {
            override fun onClick(view: View, position: Int) {
                println("Download click tapped.")
                if (view.downloadButton.visibility == View.VISIBLE) {
                    viewModel.downloadServerFile(listOfBooks.get(position).fileName, downloadButton)
                    Toast.makeText(this@LibraryCollectionActivity, "Downloading the file...", Toast.LENGTH_LONG)

                } else {
                    val displayDocumentViewer = Intent(this@LibraryCollectionActivity, DocumentViewerActivity::class.java )
                    displayDocumentViewer.putExtra(IntentPassIdentifiers.PDF_FILE_PATH, baseContext.filesDir.path + "/"+ listOfBooks.get(position).fileName )
                    startActivity(displayDocumentViewer)
                }
            }}))
    }
}

/// Interface ClickListener
interface ClickListener {
    fun onClick(view: View, position: Int)
}

