package com.susaeta.susaetaon.controllers

import android.app.DownloadManager.ACTION_DOWNLOAD_COMPLETE
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.susaeta.susaetaon.R
import com.susaeta.susaetaon.R.id.my_recycler_view
import com.susaeta.susaetaon.models.Book
import com.susaeta.susaetaon.utils.IntentPassIdentifiers
import com.susaeta.susaetaon.viewModels.BookLibraryRecyclerViewAdapter
import com.susaeta.susaetaon.viewModels.LibraryViewModel
import kotlinx.android.synthetic.main.activity_library_collection.*


class LibraryCollectionActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var viewModel: LibraryViewModel
            lateinit var onCompleteReceiver: BroadcastReceiver

    lateinit var listOfBooks: List<Book>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library_collection)

        viewModel = LibraryViewModel(this.baseContext)
        listOfBooks = intent.extras.get(IntentPassIdentifiers.BOOK_COLLECTION) as List<Book>

        onCompleteReceiver = object : BroadcastReceiver() {
            override fun onReceive(contxt: Context?, intent: Intent?) {
                when(intent?.action) {
                    "asdf" -> refreshAllThumbnailImages()
                }
            }
        }

        registerReceiver(onComplete, IntentFilter("asdf"))


        for (book in listOfBooks) {
            viewModel.downloadServerFile( book.thumbnailImageName , true)
        }

        LocalBroadcastManager.getInstance(this).unregisterReceiver(onComplete)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(onCompleteReceiver)
    }

    fun refreshAllThumbnailImages() {
        println("Refreshing...")
        val gridLayout = GridLayoutManager(this, 2)
        gridLayout.paddingRight.and(40)
        viewManager = gridLayout
        viewAdapter = BookLibraryRecyclerViewAdapter(listOfBooks, null, this.baseContext)

        recyclerView = my_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}
