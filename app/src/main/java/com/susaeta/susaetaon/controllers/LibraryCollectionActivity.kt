package com.susaeta.susaetaon.controllers

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.susaeta.susaetaon.R
import com.susaeta.susaetaon.models.Book
import com.susaeta.susaetaon.services.FileManager
import com.susaeta.susaetaon.utils.IntentPassIdentifiers
import com.susaeta.susaetaon.viewModels.BookLibraryRecyclerViewAdapter
import com.susaeta.susaetaon.viewModels.LibraryViewModel
import kotlinx.android.synthetic.main.activity_library_collection.*
import kotlinx.android.synthetic.main.fragment_item.view.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        var spanCountColumns = 2
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

        recyclerView.addOnItemTouchListener(LibraryRecycleTouchListener(baseContext, recyclerView, object: ClickListener {
            override fun onClick(view: View, position: Int) {
                println("Download click tapped.")
                downloadPDF(position, view)
            }
        } ))
    }

    private fun downloadPDF(position: Int, view: View) {
        viewModel.downloadServerFile("http://susaetaon.com:8080/libros/978994512517.pdf").enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                println("Download error.")
                Toast.makeText(applicationContext, listOfBooks.get(position).fileName + " can't download :/ !", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                println("Download successful.")
                FileManager.saveFileOnDevice(baseContext.filesDir.path, listOfBooks.get(position).fileName, response, false)
                if (view.downloadButton.visibility == View.VISIBLE) {
                    view.downloadButton.visibility = View.INVISIBLE
                }
            }
        })
    }
}

/// Interface ClickListener
interface ClickListener {
    fun onClick(view: View, position: Int)
}

