package com.shuyu.github.kotlin.ui.adapter

/**
 * 弹出选择列表Item的适配器
 * Created by guoshuyu
 * Date: 2018-10-22
 */

import android.content.Context
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.shuyu.github.kotlin.R
import kotlinx.android.synthetic.main.layout_item_default_drop_down.view.*


class ListDropDownAdapter(private val context: Context, private val list: List<String>) : BaseAdapter() {
    private var checkItemPosition = 0

    fun setCheckItem(position: Int) {
        checkItemPosition = position
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convert: View?, parent: ViewGroup): View {
        var convertView = convert
        var viewHolder: ViewHolder?
        if (convertView != null) {
            viewHolder = convertView.tag as ViewHolder
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_default_drop_down, null)
            viewHolder = ViewHolder()
            viewHolder.text = convertView.item_default_drop_text
            convertView.tag = viewHolder
        }
        fillValue(position, viewHolder)
        return convertView!!
    }

    private fun fillValue(position: Int, viewHolder: ViewHolder?) {
        viewHolder?.text?.text = list[position]
        if (checkItemPosition != -1) {
            if (checkItemPosition == position) {
                viewHolder?.text?.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                viewHolder?.text?.setBackgroundResource(R.color.check_bg)
            } else {
                viewHolder?.text?.setTextColor(ContextCompat.getColor(context, R.color.subTextColor))
                viewHolder?.text?.setBackgroundResource(R.color.white)
            }
        }
    }

    internal class ViewHolder {
        var text: TextView? = null
    }
}