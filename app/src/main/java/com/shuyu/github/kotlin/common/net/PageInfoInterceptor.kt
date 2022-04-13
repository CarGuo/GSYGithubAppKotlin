package com.shuyu.github.kotlin.common.net

import android.net.Uri
import okhttp3.Interceptor
import okhttp3.Response


/**
 * 页面信息提取拦截
 * Created by guoshuyu
 * Date: 2018-10-24
 */
class PageInfoInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val linkString: String? = response.headers()["Link"]

        val pageInfo = PageInfo()

        linkString?.apply {
            val links = this.split(",")
            links.forEach {
                when {
                    it.contains("prev") -> {
                        pageInfo.prev = parseNumber(it)
                    }
                    it.contains("next") -> {
                        pageInfo.next = parseNumber(it)
                    }
                    it.contains("last") -> {
                        pageInfo.last = parseNumber(it)
                    }
                    it.contains("first") -> {
                        pageInfo.first = parseNumber(it)
                    }
                }
            }
        }
        return response.newBuilder().addHeader("page_info", GsonUtils.toJsonString(pageInfo)).build()
    }

    private fun parseNumber(item: String?): Int {
        if (item == null) {
            return -1
        }
        val startFlag = "<"
        val endFlag = ">"
        val startIndex = item.indexOf(startFlag)
        val endStart = item.indexOf(endFlag)
        if (startIndex <= 0 || endStart <= 0) {
            return -1
        }
        val startStart = startIndex + startFlag.length
        val url = item.substring(startStart, endStart)
        val value = Uri.parse(url).getQueryParameter("page")
        return value!!.toInt()
    }
}

data class PageInfo(
        var prev: Int = -1,
        var next: Int = -1,
        var last: Int = -1,
        var first: Int = -1
)