package com.susaeta.susaetaon.controllers


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.susaeta.susaetaon.R
import kotlinx.android.synthetic.main.activity_serial_code_validator.*
import com.susaeta.susaetaon.viewModels.SerialCodeValidatorViewModel

class SerialCodeValidationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serial_code_validator)

        val model = SerialCodeValidatorViewModel()

        //Validating the editText has been a text.
       /* if (serialCodeFromEditText == "") {
            validateButton.isEnabled = false
        }*/

        validateButton.setOnClickListener({
            model.validateSerial( serialEditText.text.toString()) {
                println("Collection books has "+ it.count() +" books.")
              if (it.count() > 0 ) {
                  val serialToLibraryTransition = Intent(this@SerialCodeValidationActivity, LibraryCollectionActivity::class.java)
                  startActivity(serialToLibraryTransition)
              }
            }
        })
    }
}
