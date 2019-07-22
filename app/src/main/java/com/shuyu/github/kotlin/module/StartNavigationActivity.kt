package com.shuyu.github.kotlin.module

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.shuyu.github.kotlin.BuildConfig
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.module.service.LocalService
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * 通过 navigation 实现的首页
 * Created by guoshuyu
 * Date: 2018-11-02
 */
class StartNavigationActivity : AppCompatActivity(), HasSupportFragmentInjector {

    // 当 Fragment 调用 AndroidSupportInjection.inject(this)时
    // 从Activity获取一个DispatchingAndroidInjector<Fragment>，并将Fragment传递给inject(Fragment)
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_navigation)

        if(BuildConfig.DEBUG) {
            //如果是调试版本，启动后台服务测试AIDL
            startService(Intent(this, LocalService::class.java))
        }
    }

    //实现 HasSupportFragmentInjector 的接口，表示有Fragment需要注入
    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onBackPressed() {
        val fragment = supportFragmentManager.primaryNavigationFragment
        if (fragment is NavHostFragment) {
            if (fragment.navController.currentDestination?.id == R.id.loginFragment) {
                super.onBackPressed()
            }
        }
    }
}