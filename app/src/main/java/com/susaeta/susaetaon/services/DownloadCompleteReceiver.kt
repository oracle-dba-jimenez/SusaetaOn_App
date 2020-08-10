package com.susaeta.susaetaon.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class DownloadCompleteReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
       Toast.makeText(context, "Download complete from download broadcaster", Toast.LENGTH_LONG)
    }
}
