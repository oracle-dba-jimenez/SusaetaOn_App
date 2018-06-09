package com.susaeta.susaetaon.viewModels

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton

import com.susaeta.susaetaon.view.ItemFragment.OnListFragmentInteractionListener
import com.susaeta.susaetaon.R
import com.susaeta.susaetaon.models.Book
import com.susaeta.susaetaon.utils.Utilities

import kotlinx.android.synthetic.main.fragment_item.view.*
import java.io.File
import java.net.URI

class BookLibraryRecyclerViewAdapter(
        private val mValues: List<Book>,
        private val mListener: OnListFragmentInteractionListener?,
        private val context: Context)
    : RecyclerView.Adapter<BookLibraryRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Book
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]

        //TODO: Change to local directoy file
      //  holder.mImageThumbnail.setImageURI(Uri.fromFile(File(URI(item.thumbnailImageName))))

        val nameFile = "/" + Utilities.getNameFileFrom(item.thumbnailImageName)
        holder.mImageThumbnail.setImageURI(Uri.parse(context.filesDir.path + nameFile))

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mImageThumbnail: ImageButton = mView.thumbnailImage
    }
}
