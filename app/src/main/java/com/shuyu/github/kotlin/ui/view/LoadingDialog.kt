package com.shuyu.github.kotlin.ui.view

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.View
import com.github.ybq.android.spinkit.style.Wave
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.LayoutLoadingDialogBinding

/**
 * Loading状态弹框
 * Created by guoshuyu
 * Date: 2018-10-08
 */
class LoadingDialog : Dialog {

    constructor(context: Context) : super(context)

    constructor(context: Context, theme: Int) : super(context, theme)

    var vb: LayoutLoadingDialogBinding = LayoutLoadingDialogBinding.inflate(layoutInflater)

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        vb.loadingBar.setIndeterminateDrawable(Wave())
        super.onWindowFocusChanged(hasFocus)
    }

    companion object {

        fun showDialog(
            context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?
        ): LoadingDialog? {
            return showDialog(context, null, cancelable, cancelListener)
        }

        fun showDialog(
            context: Context,
            message: CharSequence?,
            cancelable: Boolean,
            cancelListener: DialogInterface.OnCancelListener?
        ): LoadingDialog? {
            val dialog = LoadingDialog(context, R.style.LoadingDialog)
            dialog.setContentView(R.layout.layout_loading_dialog)
            if (message.isNullOrBlank()) {
                dialog.vb.loadingMessage?.visibility = View.GONE
            } else {
                dialog.vb.loadingMessage?.text = message
            }
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(cancelable)
            dialog.setOnCancelListener(cancelListener)
            dialog.window?.attributes?.gravity = Gravity.CENTER
            val lp = dialog.window?.attributes
            lp?.dimAmount = 0.2f
            dialog.window?.attributes = lp
            dialog.show()
            return dialog
        }
    }

}