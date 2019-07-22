package com.shuyu.github.kotlin.ui.adapter

import android.content.Context
import androidx.core.content.ContextCompat
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.view.setPadding
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.utils.dp

/**
 * 纯文本的ListView适配器
 * Created by guoshuyu
 * Date: 2018-11-01
 */

class TextListAdapter(private val context: Context, private val dataList: ArrayList<String>) : BaseAdapter() {

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder: ViewHolder
        var view: View? = convertView

        if (view == null) {
            view = TextView(context)
            view.setPadding(10.dp)
            view.gravity = Gravity.CENTER
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }

        val fontSize = context.resources.getDimension(R.dimen.normalTextSize)
        viewHolder.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
        viewHolder.textView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))

        viewHolder.textView.text = dataList[position]

        return view
    }

    data class ViewHolder(val textView: TextView)
}