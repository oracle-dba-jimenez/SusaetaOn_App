package com.susaeta.susaetaon.viewModels

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.susaeta.susaetaon.R
import com.susaeta.susaetaon.models.Book
import com.susaeta.susaetaon.services.FileManager
import com.susaeta.susaetaon.utils.ErrorMessage
import com.susaeta.susaetaon.utils.Utilities
import com.susaeta.susaetaon.view.ItemFragment.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_item.view.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        viewModel.downloadServerFile( item.thumbnailImageName , true).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                println("File donwloading ... ${item.thumbnailImageName}")
                FileManager.saveFileOnDevice(context.filesDir.path, item.thumbnailImageName, response, true)

                println("Refreshing...")
                val nameFile = "/" + Utilities.getNameFileFrom(item.thumbnailImageName)
                val imageLocation = FileManager.getRelativeLocationPath(context.filesDir.path + nameFile)
                holder.mImageThumbnail.setImageURI(imageLocation)
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                println(ErrorMessage.CANT_DOWNLOAD_FILE)
            }
        })

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
