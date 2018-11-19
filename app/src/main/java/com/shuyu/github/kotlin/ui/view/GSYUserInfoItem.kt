package com.shuyu.github.kotlin.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.shuyu.github.kotlin.R
import kotlinx.android.synthetic.main.layout_info_item.view.*

/**
 * Created by guoshuyu
 * Date: 2018-11-19
 */
class GSYUserInfoItem : LinearLayout {

    var infoIcons: String? = ""
        set(value) {
            this.info_item_icon.text = value ?: "---"
        }
    var infoTitle: String? = ""
        set(value) {
            this.info_item_title.text = value ?: "---"
        }
    var infoValue: String? = ""
        set(value) {
            this.info_item_content.text = value ?: "---"
        }

    var infoClick: View.OnClickListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_info_item, this)
        info_item_card.setOnClickListener {
            infoClick?.onClick(this)
        }
    }


}
