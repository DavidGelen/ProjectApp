package wwe.com.search

import android.content.Context
import wwe.com.R

/**
 * @name ProjectApp
 * @package name：wwe.com.search
 * @anthor DavidZhang
 * @time 2019/1/11 17:30
 * @class describe
 */
class SearchEngine(private val context: Context) {
    companion object {
        val SLEEP_TIME = 2000L
    }

    private val strings = this.context.resources.getStringArray(R.array.transfer_recurring_frequency)

    fun search(text: String) = this.strings.filter { it.toLowerCase().contains(text.toLowerCase()) }.also {
        Thread.sleep(SearchEngine.SLEEP_TIME)
    }
}