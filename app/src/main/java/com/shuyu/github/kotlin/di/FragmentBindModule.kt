package com.shuyu.github.kotlin.di

import com.shuyu.github.kotlin.module.dynamic.DynamicFragment
import com.shuyu.github.kotlin.module.login.LoginFragment
import com.shuyu.github.kotlin.module.my.MyFragment
import com.shuyu.github.kotlin.module.trend.TrendFragment
import com.shuyu.github.kotlin.module.welcome.WelcomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Fragment注入
 * Created by guoshuyu
 * Date: 2018-09-30
 */


@Module
abstract class MainFragmentBindModule {
    @ContributesAndroidInjector
    abstract fun contributeDynamicFragment(): DynamicFragment

    @ContributesAndroidInjector
    abstract fun contributeTrendFragment(): TrendFragment

    @ContributesAndroidInjector
    abstract fun contributeMyFragment(): MyFragment
}

@Module
abstract class StartFragmentBindModule {

    @ContributesAndroidInjector
    abstract fun contributeWelcomeFragment(): WelcomeFragment

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

}