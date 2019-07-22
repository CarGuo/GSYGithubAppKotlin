package com.shuyu.github.kotlin.ui.view

import android.content.Context
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.utils.dp


/**
 * RecyclerView实现的横向TextView列表
 * Created by guoshuyu
 * Date: 2018-10-26
 */

class HorizontalTextList : FrameLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    val listView = RecyclerView(context)
    val list: ArrayList<String> = arrayListOf()
    var itemClick: AdapterView.OnItemClickListener? = null

    init {

        val listParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        listView.adapter = TextItemAdapter(list)
        listView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        addView(listView, listParams)
    }

    inner class TextItemAdapter(private val dataList: ArrayList<String>) : RecyclerView.Adapter<TextItemAdapter.VH>() {

        inner class VH(val v: TextView) : RecyclerView.ViewHolder(v)

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.v.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
            val res = resources
            val fontSize = res.getDimension(R.dimen.smallTextSize)
            holder.v.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)

            holder.v.text = dataList[position] + "   > "


            holder.itemView.setOnClickListener {
                itemClick?.onItemClick(null, it, position, 0)
            }
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val textView = TextView(context)
            textView.setPadding(10.dp)
            textView.gravity = Gravity.CENTER
            val layoutParams = RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
            textView.layoutParams = layoutParams
            return VH(textView)
        }
    }
}

