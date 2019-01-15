package wwe.com.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.InputMethodManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_editt_text_search.*
import wwe.com.R
import java.util.concurrent.TimeUnit

/**
 * @name ProjectApp
 * @package name：wwe.com.search
 * @anthor DavidZhang
 * @time 2019/1/11 18:11
 * @class describe
 */
class EdittTextSearchActivity : AppCompatActivity() {

    private val searchEngine by lazy(LazyThreadSafetyMode.NONE) { SearchEngine(this) }

    private var disposable: Disposable? = null

    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, EdittTextSearchActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editt_text_search)
    }

    /**Activity可见*/
    override fun onStart() {
        super.onStart()
        val observable = Observable.merge(this.createButtonObservable(), this.createTextWatchObservable())
        this.disposable = observable
            .observeOn(AndroidSchedulers.mainThread())
            //.doOnNext { this.showProgress() }
            .observeOn(Schedulers.io())
            .map { this.searchEngine.search(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                // this.hideProgress()
                //this.showSearchResult(it)

                Log.d("EdittTextSearchActivity", "it -> $it")
            }
    }

    private fun createTextWatchObservable(minLength: Int = 2, delayTime: Long = 1000) = Observable.create<String> {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.toString()?.run { it.onNext(this) }
            }
        }
        this.textSearch.addTextChangedListener(textWatcher)
        it.setCancellable { this.textSearch.removeTextChangedListener(textWatcher) }
    }.filter { it.length >= minLength }.debounce(delayTime, TimeUnit.MILLISECONDS)

    private fun createButtonObservable() = Observable.create<String> {
        searchBtn.setOnClickListener { _ ->
            hideKeyboard()
            it.onNext(this.textSearch.text.toString()) //it代指发射者
        }
        it.setCancellable {
            searchBtn.setOnClickListener(null)
        }
    }

    private fun hideKeyboard() {
        this.currentFocus?.let {
            getInputManager().hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun getInputManager(): InputMethodManager =
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    override fun onStop() {
        if (this.disposable?.isDisposed == false) {
            this.disposable?.isDisposed
        }
        super.onStop()
    }
}
