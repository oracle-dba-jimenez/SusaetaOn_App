package com.susaeta.susaetaon.controllers


import android.content.Intent
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.Toast
import com.susaeta.susaetaon.R
import kotlinx.android.synthetic.main.activity_serial_code_validator.*
import com.susaeta.susaetaon.viewModels.SerialCodeValidatorViewModel

class SerialCodeValidationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serial_code_validator)

        val model = SerialCodeValidatorViewModel(this.baseContext)

        //TODO Validar que el textEdit tenga texto para activar el button.

        validateButton.setOnClickListener({
            model.validateSerial( serialEditText.text.toString()) {
                println("Collection books has "+ it.count() +" books.")
                if (it.count() > 0 ) {
                    val serialToLibraryTransition = Intent(this@SerialCodeValidationActivity, LibraryCollectionActivity::class.java)
                    startActivity(serialToLibraryTransition)
                } else {
                    displayError("El código proporcionado no es valido favor verificar.")

                }
            }
        })
    }

    private fun displayError(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.TOP, 10, 30)
        toast.view.setBackgroundColor(Color.RED)
        toast.view.setPadding(35, 5, 35, 4)
        toast.show()
    }
}
