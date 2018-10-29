package com.shuyu.github.kotlin.di

import com.shuyu.github.kotlin.di.annotation.ActivityScope
import com.shuyu.github.kotlin.module.StartActivity
import com.shuyu.github.kotlin.module.code.CodeDetailActivity
import com.shuyu.github.kotlin.module.main.MainActivity
import com.shuyu.github.kotlin.module.person.PersonActivity
import com.shuyu.github.kotlin.module.repos.ReposDetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Activity 注入，并且提供Fragment注入绑定
 * Created by guoshuyu
 * Date: 2018-09-30
 */
@Module
abstract class ActivityBindModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [StartFragmentBindModule::class])
    abstract fun startActivityInjector(): StartActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class, MainFragmentBindModule::class])
    abstract fun mainActivityInjector(): MainActivity


    @ActivityScope
    @ContributesAndroidInjector(modules = [PersonFragmentBindModule::class])
    abstract fun personActivityInjector(): PersonActivity


    @ActivityScope
    @ContributesAndroidInjector(modules = [ReposDetailFragmentBindModule::class, ReposDetailModule::class])
    abstract fun reposDetailActivityInjector(): ReposDetailActivity


    @ActivityScope
    @ContributesAndroidInjector(modules = [CodeDetailFragmentBindModule::class])
    abstract fun codeDetailActivityInjector(): CodeDetailActivity

}