package com.shuyu.github.kotlin.di

import com.shuyu.github.kotlin.module.dynamic.DynamicFragment
import com.shuyu.github.kotlin.module.my.MyFragment
import com.shuyu.github.kotlin.module.trend.TrendFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
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