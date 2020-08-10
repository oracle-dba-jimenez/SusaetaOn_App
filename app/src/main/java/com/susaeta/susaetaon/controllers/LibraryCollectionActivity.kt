package com.susaeta.susaetaon.controllers

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.susaeta.susaetaon.R
import com.susaeta.susaetaon.models.Book
import com.susaeta.susaetaon.utils.IntentPassIdentifiers
import com.susaeta.susaetaon.viewModels.BookLibraryRecyclerViewAdapter
import com.susaeta.susaetaon.viewModels.LibraryViewModel
import kotlinx.android.synthetic.main.activity_library_collection.*
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

        //StrictMode$AndroidBlockGuardPolicy
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        viewModel = LibraryViewModel(baseContext)
        listOfBooks = (intent.extras?.get(IntentPassIdentifiers.BOOK_COLLECTION) as List<Book>).distinct()

        var spanCountColumns = 3
        if (resources.configuration.orientation  == Configuration.ORIENTATION_LANDSCAPE) {
            spanCountColumns = 4
        }

        val gridLayout = GridLayoutManager(baseContext, spanCountColumns)
        viewManager = gridLayout
        viewAdapter = BookLibraryRecyclerViewAdapter(listOfBooks.distinct(), null, baseContext)

        recyclerView = my_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        //TODO: Intentar sacar este listener de aqui y delegar esta responsabilidad.
        recyclerView.addOnItemTouchListener(LibraryRecycleTouchListener(baseContext, object: ClickListener {
            override fun onClick(view: View, position: Int) {
                if (view.downloadButton.visibility == View.VISIBLE) {
                    val serialCode =  intent.extras?.get(IntentPassIdentifiers.SERIAL_CODE) as String
                    viewModel.downloadServerFile(listOfBooks[position].fileName, view.downloadButton, serialCode)
                    view.downloadButton.text = getString(R.string.downloading_progress_message)

                } else {
                    val displayDocumentViewer = Intent(this@LibraryCollectionActivity, DocumentViewerActivity::class.java )
                    displayDocumentViewer.putExtra(IntentPassIdentifiers.PDF_FILE_PATH,
                            baseContext.filesDir.path + "/"+ listOfBooks.get(position).fileName )
                    displayDocumentViewer.putExtra(IntentPassIdentifiers.ENCRYPT_KEY, listOfBooks[position].key)
                    startActivity(displayDocumentViewer)
                }
            }}
        ))
    }
}

/// Interface ClickListener
interface ClickListener {
    fun onClick(view: View, position: Int)
}

