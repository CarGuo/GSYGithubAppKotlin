package com.shuyu.github.kotlin.di

import com.shuyu.github.kotlin.di.annotation.ActivityScope
import com.shuyu.github.kotlin.module.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by guoshuyu
 * Date: 2018-09-30
 */
@Module
abstract class ActivityBindModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class, MainFragmentBindModule::class])
    abstract fun mainActivityInjector(): MainActivity

}