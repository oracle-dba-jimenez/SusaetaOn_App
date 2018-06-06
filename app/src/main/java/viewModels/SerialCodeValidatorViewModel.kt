package viewModels

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import models.Book
import services.SusaetaRepositoryProvider

class SerialCodeValidatorViewModel {

    lateinit var books: List<Book>

    fun validateSerial(code: String): Boolean {
        val repository = SusaetaRepositoryProvider.provideSearchRepository()

        books = mutableListOf<Book>()

        repository.getCollectionBooks().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    this.books = result.items
                }, { error ->
                    error.printStackTrace()
                })
        return books.count()  > 0
    }

    fun downloadThumnail
}