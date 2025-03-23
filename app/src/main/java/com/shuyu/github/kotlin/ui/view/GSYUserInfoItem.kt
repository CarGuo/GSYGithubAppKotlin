package com.shuyu.github.kotlin.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.LayoutInfoItemBinding

/**
 * Created by guoshuyu
 * Date: 2018-11-19
 */
class GSYUserInfoItem : LinearLayout {

    var infoIcons: String? = ""
        set(value) {
            vb.infoItemIcon.text = value ?: "---"
        }
    var infoTitle: String? = ""
        set(value) {
            vb.infoItemTitle.text = value ?: "---"
        }
    var infoValue: String? = ""
        set(value) {
            vb.infoItemContent.text = value ?: "---"
        }

    var infoClick: View.OnClickListener? = null

    private var vb: LayoutInfoItemBinding =
        LayoutInfoItemBinding.inflate(LayoutInflater.from(context));

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    init {;
        vb.infoItemCard.setOnClickListener {
            infoClick?.onClick(this)
        }
    }


}
