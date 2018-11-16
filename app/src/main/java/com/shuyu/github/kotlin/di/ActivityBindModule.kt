package com.shuyu.github.kotlin.di

import com.shuyu.github.kotlin.di.annotation.ActivityScope
import com.shuyu.github.kotlin.module.StartNavigationActivity
import com.shuyu.github.kotlin.module.code.CodeDetailActivity
import com.shuyu.github.kotlin.module.issue.IssueDetailActivity
import com.shuyu.github.kotlin.module.list.GeneralListActivity
import com.shuyu.github.kotlin.module.main.MainActivity
import com.shuyu.github.kotlin.module.notify.NotifyActivity
import com.shuyu.github.kotlin.module.person.PersonActivity
import com.shuyu.github.kotlin.module.push.PushDetailActivity
import com.shuyu.github.kotlin.module.repos.ReposDetailActivity
import com.shuyu.github.kotlin.module.search.SearchActivity
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
    abstract fun StartNavigationActivityInjector(): StartNavigationActivity

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


    @ActivityScope
    @ContributesAndroidInjector(modules = [IssueDetailFragmentBindModule::class])
    abstract fun issueDetailActivityInjector(): IssueDetailActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [SearchFragmentBindModule::class])
    abstract fun searchActivityInjector(): SearchActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [GeneralListFragmentBindModule::class])
    abstract fun generalListActivityInjector(): GeneralListActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [NotifyFragmentBindModule::class])
    abstract fun notifyActivityInjector(): NotifyActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [PushDetailFragmentBindModule::class])
    abstract fun pushDetailActivityInjector(): PushDetailActivity

}