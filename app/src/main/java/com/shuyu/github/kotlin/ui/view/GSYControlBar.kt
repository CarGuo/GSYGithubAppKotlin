package com.shuyu.github.kotlin.ui.view

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setPadding
import com.mikepenz.iconics.view.IconicsTextView
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.utils.dp

/**
 * Created by guoshuyu
 * Date: 2018-10-29
 */

class GSYControlBar : CardView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    val listView = RecyclerView(context)
    val list: ArrayList<String> = arrayListOf("{GSY-REPOS_ITEM_FILE} star", "{GSY-REPOS_ITEM_FILE} star", "{GSY-REPOS_ITEM_FILE} star", "{GSY-REPOS_ITEM_FILE} star")
    var itemClick: AdapterView.OnItemClickListener? = null

    init {

        setContentPadding(10.dp, 5.dp, 10.dp, 5.dp)

        val listParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        listView.adapter = TextItemAdapter(list)
        listView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
        addView(listView, listParams)
    }

    inner class TextItemAdapter(private val dataList: ArrayList<String>) : RecyclerView.Adapter<TextItemAdapter.VH>() {

        inner class VH(val v: TextView) : RecyclerView.ViewHolder(v)

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.v.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
            val res = resources
            val fontSize = res.getDimension(R.dimen.smallTextSize)
            holder.v.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)

            holder.v.text = dataList[position]

            holder.itemView.setOnClickListener {
                itemClick?.onItemClick(null, it, position, 0)
            }
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val textView = IconicsTextView(context)
            textView.setPadding(10.dp)

            textView.setBackgroundResource(R.drawable.ripple_bg)

            textView.gravity = Gravity.CENTER
            val layoutParams = RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
            textView.layoutParams = layoutParams
            return VH(textView)
        }
    }
}
