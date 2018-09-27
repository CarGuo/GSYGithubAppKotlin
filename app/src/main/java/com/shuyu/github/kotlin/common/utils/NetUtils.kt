package com.shuyu.github.kotlin.common.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log

/**
 * 检测实时网络状态
 *
 *
 * @author Administrator
 */
object NetUtils {

    val TYPE_DISCONNECT = 0

    val TYPE_WIFI = 1

    val TYPE_MOBILE = 2


    /**
     * 检测当前网络状态
     */
    fun checkNet(context: Context): Int {
        var status = TYPE_DISCONNECT
        try {
            val connectivity = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = connectivity.activeNetworkInfo
            if (info != null && info.isAvailable) {
                if (info.state == NetworkInfo.State.CONNECTED) {
                    if (info.type == ConnectivityManager.TYPE_WIFI)
                        status = TYPE_WIFI
                    if (info.type == ConnectivityManager.TYPE_MOBILE)
                        status = TYPE_MOBILE
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            status = TYPE_DISCONNECT
        }

        return status
    }

}
