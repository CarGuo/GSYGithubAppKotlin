package com.shuyu.github.kotlin.di

import com.shuyu.github.kotlin.module.dynamic.DynamicFragment
import com.shuyu.github.kotlin.module.login.LoginFragment
import com.shuyu.github.kotlin.module.my.MyFragment
import com.shuyu.github.kotlin.module.person.PersonFragment
import com.shuyu.github.kotlin.module.repos.action.ReposActionListFragment
import com.shuyu.github.kotlin.module.repos.file.ReposFileListFragment
import com.shuyu.github.kotlin.module.repos.issue.ReposIssueListFragment
import com.shuyu.github.kotlin.module.repos.readme.ReposReadmeFragment
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


@Module
abstract class PersonFragmentBindModule {

    @ContributesAndroidInjector
    abstract fun contributeWelcomeFragment(): PersonFragment

}

@Module
abstract class ReposDetailFragmentBindModule {

    @ContributesAndroidInjector
    abstract fun contributeReposReadmeFragment(): ReposReadmeFragment


    @ContributesAndroidInjector
    abstract fun contributeReposIssueListFragment(): ReposIssueListFragment


    @ContributesAndroidInjector
    abstract fun contributeReposActionListFragment(): ReposActionListFragment


    @ContributesAndroidInjector
    abstract fun contributeReposFileListFragment(): ReposFileListFragment
}