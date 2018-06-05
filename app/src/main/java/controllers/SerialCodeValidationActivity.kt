package com.susaeta.susaetaon.controllers


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.susaeta.susaetaon.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_serial_code_validator.*
import services.SusaetaRepositoryProvider

class SerialCodeValidationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serial_code_validator)


        validateButton.setOnClickListener({

            val repository = SusaetaRepositoryProvider.provideSearchRepository()

            repository.getCollectionBooks().observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe( {
                        result -> print("Resultado:: trajo esta cantidad de registros: "+ result.items.count())
                    }, {
                        error -> error.printStackTrace()
                    })
        })
    }
}
