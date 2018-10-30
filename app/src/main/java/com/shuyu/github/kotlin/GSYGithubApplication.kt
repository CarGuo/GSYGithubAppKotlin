package com.shuyu.github.kotlin

import android.app.Activity
import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.mikepenz.iconics.Iconics
import com.mikepenz.iconics.context.IconicsContextWrapper
import com.shuyu.github.kotlin.common.style.GSYIconfont
import com.shuyu.github.kotlin.common.style.MarkDownConfig
import com.shuyu.github.kotlin.di.AppInjector
import com.shuyu.gsygiideloader.GSYGlideImageLoader
import com.shuyu.gsyimageloader.GSYImageLoaderManager
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject
import kotlin.properties.Delegates


class GSYGithubApplication : Application(), HasActivityInjector {

    companion object {
        var instance: GSYGithubApplication by Delegates.notNull()
    }

    /**
     * 分发Activity的注入
     */
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(IconicsContextWrapper.wrap(base))
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        ///初始化路由
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)

        //Application级别注入
        AppInjector.init(this)

        ///初始化图标库
        Iconics.init(applicationContext)
        Iconics.registerFont(GSYIconfont())

        ///初始化图片加载
        GSYImageLoaderManager.initialize(GSYGlideImageLoader(this))


        MarkDownConfig.init(this)


    }

    override fun activityInjector() = dispatchingAndroidInjector
}