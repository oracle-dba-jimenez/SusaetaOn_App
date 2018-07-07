package com.susaeta.susaetaon.controllers

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent

class LibraryRecycleTouchListener(context: Context, var clickListener: ClickListener) : RecyclerView.OnItemTouchListener{

     var gestureDetector: GestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
             return true
         }
     })

    override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent?): Boolean {
        val child = rv!!.findChildViewUnder(e!!.getX(), e!!.getY())
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onClick(child, rv.getChildAdapterPosition(child))
        }

        return false
    }

    override fun onTouchEvent(rv: RecyclerView?, e: MotionEvent?) { }
    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) { }
}