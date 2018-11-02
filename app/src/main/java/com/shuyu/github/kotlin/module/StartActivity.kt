package com.shuyu.github.kotlin.module

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.shuyu.github.kotlin.R
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Created by guoshuyu
 * Date: 2018-09-30
 */
class StartActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        window?.setBackgroundDrawable(null)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}