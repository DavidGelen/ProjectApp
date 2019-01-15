package wwe.com

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import wwe.com.search.EdittTextSearchActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var linkStr: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tick1.setOnClickListener(this)
    }

    fun linkIsValite(): Boolean {
        return ! linkStr.isNullOrBlank()
    }

    override fun onClick(view: View?) {
        view?.let {
            val id = it.id
            when (id) {
                R.id.tick1 ->
                    EdittTextSearchActivity.newInstance(this)

            }
        }
    }
}
