package com.susaeta.susaetaon.controllers

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Gravity
import android.widget.ProgressBar
import android.widget.Toast
import com.susaeta.susaetaon.R
import com.susaeta.susaetaon.services.FileManager
import com.susaeta.susaetaon.utils.ErrorMessage
import com.susaeta.susaetaon.utils.IntentPassIdentifiers
import com.susaeta.susaetaon.utils.Utilities
import com.susaeta.susaetaon.viewModels.SerialCodeValidatorViewModel
import kotlinx.android.synthetic.main.activity_serial_code_validator.*
import java.io.Serializable
import java.util.*

class SerialCodeValidationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serial_code_validator)

        progressBar.visibility = ProgressBar.INVISIBLE
        val model = SerialCodeValidatorViewModel(this.baseContext)

        @SuppressLint("SetTextI18n")
        schoolarshipYear.text = (schoolYear()-1).toString() + " - " + schoolYear().toString()

        validateButton.setOnClickListener {
            if (Utilities.isNetworkAvailable(this.baseContext)) {
                if (serialEditText.text.isNotEmpty()) {
                    model.validateSerial(serialEditText.text.toString()) {
                        //Save serialized book list.
                        FileManager.saveSerializedBookList(baseContext, it)

                        progressBar.visibility = ProgressBar.VISIBLE
                        println("Collection books has $it.count()  books.")
                        if (it.count() > 0 ) {
                            val serialToLibraryTransitionIntent = Intent(this@SerialCodeValidationActivity, LibraryCollectionActivity::class.java)
                            serialToLibraryTransitionIntent.putExtra(IntentPassIdentifiers.BOOK_COLLECTION, it as Serializable)
                            serialToLibraryTransitionIntent.putExtra(IntentPassIdentifiers.SERIAL_CODE, serialEditText.text.toString())
                            serialEditText.text.clear()
                            startActivity(serialToLibraryTransitionIntent)
                        } else {
                            this.displayError(ErrorMessage.INVALID_CODE_ESP)
                        }
                        progressBar.visibility = ProgressBar.INVISIBLE
                    }
                }
            } else {
                displayError(getString(R.string.network_unavailable))
            }
        }
    }

    private fun displayError(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.TOP, 10, 30)
        toast.view?.setBackgroundColor(Color.RED)
        toast.view?.setPadding(35, 5, 35, 4)
        toast.show()
    }

    private fun schoolYear(): Int {
        var currentYear = Calendar.getInstance().get(Calendar.YEAR)
        if (Calendar.getInstance().get(Calendar.MONTH)+ 1 >= 8){
            currentYear += 1
        }
        return currentYear
    }
}
