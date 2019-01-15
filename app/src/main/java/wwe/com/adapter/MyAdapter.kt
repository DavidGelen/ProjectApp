package wwe.com.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import wwe.com.R

/**
 * @name ProjectApp
 * @class name：wwe.com.adapter
 * @class describe
 * @anthor David
 * @time 2019/1/15 下午3:40
 * @class describe
 */


data class SearchEngine(var name: String, var url: String, var selected: Boolean = false)

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageSearchEngine: ImageView = this.itemView.findViewById(R.id.imageSearchIcon)
    val labelSearchEngine: TextView = this.itemView.findViewById(R.id.textSearchEngine)
    val imageSelected: ImageView = this.itemView.findViewById(R.id.imageSelected)
}

class MyAdapter(
    private val context: Context,
    private val dataList: ArrayList<SearchEngine>,
    var itemClick: ((view: View) -> Unit)? = null,
    var longClick: ((view: View) -> Boolean)? = null
) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view = this.inflater.inflate(R.layout.layout_item_search_list, p0, false)
        this.itemClick?.let { view.setOnClickListener(it) }
        this.longClick?.let { view.setOnLongClickListener(it) }
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0?.let {
            val engine = this.dataList[p1]
            it.labelSearchEngine.text = engine.url
            it.imageSelected.visibility = if (engine.selected) View.VISIBLE else View.INVISIBLE
        }
    }

    private val inflater by lazy(LazyThreadSafetyMode.NONE) { LayoutInflater.from(this.context) }

    override fun getItemCount() = this.dataList.size

}