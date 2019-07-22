package com.shuyu.github.kotlin.di

import android.app.Application
import androidx.core.content.ContextCompat
import com.mikepenz.iconics.IconicsDrawable
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.style.GSYIconfont
import com.shuyu.github.kotlin.di.annotation.FragmentQualifier
import dagger.Module
import dagger.Provides
import devlight.io.library.ntb.NavigationTabBar

/**
 * 仓库Issue列表TabBar数据
 * Created by guoshuyu
 * Date: 2018-10-25
 */

@Module
class ReposIssueListModule {


    @FragmentQualifier("IssueList")
    @Provides
    fun providerReposIssueListTabModel(application: Application): List<NavigationTabBar.Model> {
        return listOf(
                NavigationTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .icon(GSYIconfont.Icon.GSY_REPOS_ITEM_ALL)
                                .color(ContextCompat.getColor(application, R.color.subTextColor))
                                .sizeDp(14),
                        ContextCompat.getColor(application, R.color.colorPrimaryLight))
                        .title(application.getString(R.string.issueAllText))
                        .build(),
                NavigationTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .icon(GSYIconfont.Icon.GSY_REPOS_ITEM_OPEN)
                                .color(ContextCompat.getColor(application, R.color.subTextColor))
                                .sizeDp(14),
                        ContextCompat.getColor(application, R.color.colorPrimaryLight))
                        .title(application.getString(R.string.issueOpenText))
                        .build(),
                NavigationTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .icon(GSYIconfont.Icon.GSY_REPOS_ITEM_CLOSE)
                                .color(ContextCompat.getColor(application, R.color.subTextColor))
                                .sizeDp(14),
                        ContextCompat.getColor(application, R.color.colorPrimaryLight))
                        .title(application.getString(R.string.issueCloseText))
                        .build()
        )

    }


    @FragmentQualifier("IssueList")
    @Provides
    fun providerStatusList(): ArrayList<String> = arrayListOf("all", "open", "closed")

}