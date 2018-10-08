package com.shuyu.github.kotlin.common.net

import android.content.Context
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.ui.view.LoadingDialog


/**
 * Created by guoshuyu
 * Date: 2018-10-08
 */
abstract class ResultProgressObserver<T>(private val context: Context) : ResultObserver<T>() {

    private var loadingDialog: LoadingDialog? = null

    private var loadingText: String? = null

    constructor(context: Context, loadingText: String?) : this(context) {
        this.loadingText = loadingText
    }

    override fun onRequestStart() {
        super.onRequestStart()
        showLoading()
    }

    override fun onRequestEnd() {
        super.onRequestEnd()
        dismissLoading()
    }

    private fun showLoading() {
        dismissLoading()
        loadingDialog = LoadingDialog.showDialog(context, context.getString(R.string.loading), false, null)
    }

    private fun  dismissLoading() {
        loadingDialog?.apply {
            if(this.isShowing) {
                this.dismiss()
            }
        }
    }
}