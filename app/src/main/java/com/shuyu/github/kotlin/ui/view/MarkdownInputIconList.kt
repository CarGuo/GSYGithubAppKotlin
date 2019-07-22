package com.shuyu.github.kotlin.ui.view

import android.content.Context
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.iconics.view.IconicsTextView
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.utils.dp


class MarkdownInputIconList : FrameLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    val listView = RecyclerView(context)
    val list: ArrayList<String> = arrayListOf("{GSY-MD_1}", "{GSY-MD_2}", "{GSY-MD_3}", "{GSY-MD_4}", "{GSY-MD_5}", "{GSY-MD_6}", "{GSY-MD_7}", "{GSY-MD_8}", "{GSY-MD_9}")
    var itemClick: AdapterView.OnItemClickListener? = null
    var editText: EditText? = null

    init {

        val listParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        listView.adapter = TextItemAdapter(list)
        listView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        addView(listView, listParams)
        itemClick = AdapterView.OnItemClickListener { parent, view, position, id ->
            var text = editText?.text ?: ""
            when (position) {
                0 -> {
                    text = "$text\n#"
                }
                1 -> {
                    text = "$text\n##"
                }
                2 -> {
                    text = "$text\n###"
                }
                3 -> {
                    text = "$text ** ** "
                }
                4 -> {
                    text = "$text * * "
                }
                5 -> {
                    text = "$text `` "
                }
                6 -> {
                    text = "$text\n```\n\n```\n"
                }
                7 -> {
                    text = "$text []() "
                }
                8 -> {
                    text = "$text\n-\n"
                }
            }
            editText?.setText(text)
        }
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
            textView.gravity = Gravity.CENTER
            textView.setBackgroundResource(R.drawable.ripple_bg)
            val layoutParams = RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
            textView.layoutParams = layoutParams
            return VH(textView)
        }
    }
}

