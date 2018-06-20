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
import com.susaeta.susaetaon.utils.IntentPassIdentifiers
import com.susaeta.susaetaon.viewModels.BookLibraryRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_library_collection.*
import kotlinx.android.synthetic.main.fragment_item.view.*

class LibraryCollectionActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    lateinit var listOfBooks: List<Book>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library_collection)

        listOfBooks = intent.extras.get(IntentPassIdentifiers.BOOK_COLLECTION) as List<Book>

        var spanCountColumns = 2
        if (getResources().getConfiguration().orientation  == Configuration.ORIENTATION_LANDSCAPE) {
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
                Toast.makeText(getApplicationContext(), listOfBooks.get(position).fileName + " is selected!", Toast.LENGTH_SHORT).show();
                if (view.downloadButton.visibility == View.VISIBLE) {
                    view.downloadButton.visibility = View.INVISIBLE
                }
            }

            override fun onLongClick(view: View, position: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        } ))
    }
}


interface ClickListener {
    fun onClick(view: View, position: Int)
    fun onLongClick(view: View, position: Int)
}
