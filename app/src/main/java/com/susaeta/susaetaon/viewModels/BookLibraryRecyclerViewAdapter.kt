package com.susaeta.susaetaon.viewModels

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.bumptech.glide.Glide
import com.susaeta.susaetaon.R
import com.susaeta.susaetaon.models.Book
import com.susaeta.susaetaon.view.ItemFragment.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_item.view.*

class BookLibraryRecyclerViewAdapter(
        private val mValues: List<Book>,
        private val mListener: OnListFragmentInteractionListener?,
        private val context: Context)
    : RecyclerView.Adapter<BookLibraryRecyclerViewAdapter.ViewHolder>() {

    private val viewModel: LibraryViewModel
    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Book
            mListener?.onListFragmentInteraction(item)
        }

        viewModel = LibraryViewModel(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        Glide.with(holder.mView).load(item.thumbnailImageName).into(holder.mImageThumbnail)

       // holder.mView.
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
