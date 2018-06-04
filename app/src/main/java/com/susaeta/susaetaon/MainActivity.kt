package com.susaeta.susaetaon

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val validateButton = findViewById<Button>(R.id.validateCodeButton)
       // validateButton.setOnClickListener(this)
      //  goToLibraryButton.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when  (p0?.id) {
            R.id.validateCodeButton -> {
                val goToLibraryIntent = Intent(this@MainActivity, SerialCodeValidationActivity::class.java)
                startActivity(goToLibraryIntent)
            }

            R.id.goToLibraryButton -> {
                val goToLibraryIntent = Intent(this@MainActivity, LibraryCollection::class.java)
                startActivity(goToLibraryIntent)
            }
            else -> {
                Toast.makeText(this, "New button no mapping", Toast.LENGTH_SHORT)
            }
        }
    }
}

