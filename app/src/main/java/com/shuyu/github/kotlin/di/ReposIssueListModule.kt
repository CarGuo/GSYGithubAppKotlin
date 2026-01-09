package com.shuyu.github.kotlin.di

import android.app.Application
import com.mikepenz.iconics.IconicsColor
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.utils.sizeDp
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.style.GSYIconfont
import com.shuyu.github.kotlin.di.annotation.FragmentQualifier
import com.shuyu.github.kotlin.ui.view.GSYTabBar
import dagger.Module
import dagger.Provides

/**
 * 仓库Issue列表TabBar数据
 * Created by guoshuyu
 * Date: 2018-10-25
 */

@Module
class ReposIssueListModule {


    @FragmentQualifier("IssueList")
    @Provides
    fun providerReposIssueListTabModel(application: Application): List<GSYTabBar.Model> {
        return listOf(
                GSYTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .icon(GSYIconfont.Icon.GSY_REPOS_ITEM_ALL)
                                .color(IconicsColor.colorInt(R.color.subTextColor))
                                .sizeDp(14))
                        .title(application.getString(R.string.issueAllText))
                        .build(),
                GSYTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .icon(GSYIconfont.Icon.GSY_REPOS_ITEM_OPEN)
                                .color(IconicsColor.colorInt(R.color.subTextColor))
                                .sizeDp(14))
                        .title(application.getString(R.string.issueOpenText))
                        .build(),
                GSYTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .icon(GSYIconfont.Icon.GSY_REPOS_ITEM_CLOSE)
                                .color(IconicsColor.colorInt(R.color.subTextColor))
                                .sizeDp(14))
                        .title(application.getString(R.string.issueCloseText))
                        .build()
        )

    }


    @FragmentQualifier("IssueList")
    @Provides
    fun providerStatusList(): ArrayList<String> = arrayListOf("all", "open", "closed")

}