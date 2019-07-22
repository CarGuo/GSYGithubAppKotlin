package com.shuyu.github.kotlin.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.alibaba.android.arouter.launcher.ARouter
import com.shuyu.github.kotlin.GSYGithubApplication
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

/**
 * Application注入，并且对Activity和Fragment提供动态注入
 * Created by guoshuyu
 * Date: 2018-09-30
 */
object AppInjector {
    fun init(githubApp: GSYGithubApplication) {

        //通过builder注入application，然后注入app
        DaggerAppComponent.builder().application(githubApp)
                .build().inject(githubApp)

        githubApp.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                handleActivity(activity)
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }
        })
    }

    private fun handleActivity(activity: Activity) {
        //注入Activity
        if (activity is HasSupportFragmentInjector) {
            AndroidInjection.inject(activity)
        }
        if (activity is ARouterInjectable) {
            ///注入ARouter参数
            ARouter.getInstance().inject(activity)
        }
        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                    object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentCreated(
                                fm: FragmentManager,
                                f: Fragment,
                                savedInstanceState: Bundle?) {
                            //注入fragment
                            if (f is Injectable) {
                                AndroidSupportInjection.inject(f)
                            }
                            if (f is ARouterInjectable) {
                                ///注入ARouter参数
                                ARouter.getInstance().inject(f)
                            }
                        }
                    }, true
            )
        }
    }
}