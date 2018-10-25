package com.shuyu.github.kotlin.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.RelativeLayout
import android.graphics.Bitmap
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import com.github.ybq.android.spinkit.SpinKitView
import com.github.ybq.android.spinkit.style.MultiplePulseRing
import com.shuyu.github.kotlin.R


class GSYWebViewContainer : RelativeLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    val webView: WebView

    val spinKit: SpinKitView

    init {
        webView = GSYWebView(context)
        webView.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        var layoutParams = RelativeLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)

        val settings = webView.settings
        settings?.javaScriptEnabled = true
        settings?.loadWithOverviewMode = true
        settings?.builtInZoomControls = false
        settings?.displayZoomControls = false
        settings?.domStorageEnabled = true
        settings?.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        settings?.setAppCacheEnabled(true)

        addView(webView, layoutParams)


        spinKit = SpinKitView(context)
        layoutParams = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        spinKit.setColor(ContextCompat.getColor(context, R.color.colorPrimary))
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)

        addView(spinKit, layoutParams)

        val animator = MultiplePulseRing()
        spinKit.setIndeterminateDrawable(animator)


        val webViewClient: WebViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                spinKit.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                spinKit.visibility = View.GONE
            }
        }

        webView.webViewClient = webViewClient


    }

}