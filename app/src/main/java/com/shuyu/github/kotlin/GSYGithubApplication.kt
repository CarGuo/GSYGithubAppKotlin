package com.shuyu.github.kotlin

import android.app.Application
import android.content.Context
import com.mikepenz.iconics.context.IconicsContextWrapper


class GSYGithubApplication : Application() {


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(IconicsContextWrapper.wrap(base))
    }

    override fun onCreate() {
        super.onCreate()
    }
}