package com.shuyu.github.kotlin

import android.app.Activity
import android.app.Application
import android.content.Context
import com.mikepenz.iconics.context.IconicsContextWrapper
import com.mikepenz.iconics.Iconics
import com.shuyu.github.kotlin.common.style.GSYIconfont
import com.shuyu.github.kotlin.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class GSYGithubApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(IconicsContextWrapper.wrap(base))
    }

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)
        ///初始化图标库
        Iconics.init(applicationContext)
        Iconics.registerFont(GSYIconfont())
    }

    override fun activityInjector() = dispatchingAndroidInjector
}