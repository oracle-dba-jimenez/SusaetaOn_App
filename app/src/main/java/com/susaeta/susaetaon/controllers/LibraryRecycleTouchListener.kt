package com.susaeta.susaetaon.controllers

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent

class LibraryRecycleTouchListener(context: Context, var clickListener: ClickListener) : RecyclerView.OnItemTouchListener{

    override fun onTouchEvent(p0: RecyclerView, p1: MotionEvent) {
        TODO("not implemented")
    }

    var gestureDetector: GestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
             return true
         }
     })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val child = rv.findChildViewUnder(e.x, e.y)
        if (child != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onClick(child, rv.getChildAdapterPosition(child))
        }
        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) { }
}