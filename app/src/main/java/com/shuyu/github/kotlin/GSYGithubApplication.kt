package com.shuyu.github.kotlin

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.alibaba.android.arouter.launcher.ARouter
import com.mikepenz.iconics.Iconics
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerImageLoader
import com.shuyu.github.kotlin.common.db.RoomFactory
import com.shuyu.github.kotlin.common.gsyimageloader.GSYImageLoaderManager
import com.shuyu.github.kotlin.common.gsyimageloader.gsygiideloader.GSYGlideImageLoader
import com.shuyu.github.kotlin.common.style.GSYIconfont
import com.shuyu.github.kotlin.common.utils.CommonUtils
import com.shuyu.github.kotlin.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject
import kotlin.properties.Delegates


class GSYGithubApplication : Application(), HasAndroidInjector {

    companion object {
        var instance: GSYGithubApplication by Delegates.notNull()
    }

    /**
     * 分发Activity的注入
     *
     * 在Activity调用AndroidInjection.inject(this)时
     * 从Application获取一个DispatchingAndroidInjector<Activity>，并将activity传递给inject(activity)
     * DispatchingAndroidInjector通过AndroidInjector.Factory创建AndroidInjector
     */
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

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
        Iconics.registerFont(GSYIconfont)

        ///初始化图片加载
        GSYImageLoaderManager.initialize(GSYGlideImageLoader(this))

        ///数据库
        RoomFactory.init(this)


        DrawerImageLoader.init(object : AbstractDrawerImageLoader() {

            override fun placeholder(ctx: Context): Drawable {
                return getDrawable(R.drawable.logo)!!
            }

            override fun placeholder(ctx: Context, tag: String?): Drawable {
                return getDrawable(R.drawable.logo)!!
            }
            override fun set(imageView: ImageView, uri: Uri, placeholder: Drawable, tag: String?) {
                CommonUtils.loadUserHeaderImage(imageView, uri.toString())
            }
        })
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}