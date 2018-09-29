package com.shuyu.github.kotlin

import android.app.Application
import android.content.Context
import com.mikepenz.iconics.context.IconicsContextWrapper
import com.mikepenz.iconics.Iconics
import com.shuyu.github.kotlin.common.style.GSYIconfont


class GSYGithubApplication : Application() {


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(IconicsContextWrapper.wrap(base))
    }

    override fun onCreate() {
        super.onCreate()

        ///初始化图标库
        Iconics.init(applicationContext)
        Iconics.registerFont(GSYIconfont())
    }
}