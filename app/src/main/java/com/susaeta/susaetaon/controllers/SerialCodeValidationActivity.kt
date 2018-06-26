package com.susaeta.susaetaon.controllers

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.ProgressBar
import android.widget.Toast
import com.susaeta.susaetaon.R
import com.susaeta.susaetaon.utils.ErrorMessage
import com.susaeta.susaetaon.utils.IntentPassIdentifiers
import com.susaeta.susaetaon.viewModels.SerialCodeValidatorViewModel
import kotlinx.android.synthetic.main.activity_serial_code_validator.*
import java.io.Serializable

class SerialCodeValidationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serial_code_validator)

        val model = SerialCodeValidatorViewModel(this.baseContext)

        //TODO Validar que el textEdit tenga texto para activar el button.

        validateButton.setOnClickListener {
            model.validateSerial("L429OKT1XJBBB2R" /*serialEditText.text.toString()*/) {
                progressBar.visibility = ProgressBar.VISIBLE
                println("Collection books has $it.count()  books.")
                if (it.count() > 0 ) {
                    val serialToLibraryTransitionIntent = Intent(this@SerialCodeValidationActivity, LibraryCollectionActivity::class.java)
                    serialToLibraryTransitionIntent.putExtra(IntentPassIdentifiers.BOOK_COLLECTION, it as Serializable)
                    startActivity(serialToLibraryTransitionIntent)
                } else {
                    displayError(ErrorMessage.INVALID_CODE_ESP)
                }
                progressBar.visibility = ProgressBar.INVISIBLE
            }
        }
    }

    private fun displayError(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.TOP, 10, 30)
        toast.view.setBackgroundColor(Color.RED)
        toast.view.setPadding(35, 5, 35, 4)
        toast.show()
    }
}
