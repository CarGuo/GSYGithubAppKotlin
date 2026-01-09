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
 * 搜索页面数据TabBar
 * Created by guoshuyu
 * Date: 2018-10-25
 */

@Module
class SearchModule {


    @FragmentQualifier("Search")
    @Provides
    fun providerReposIssueListTabModel(application: Application): List<GSYTabBar.Model> {
        return listOf(
                GSYTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .icon(GSYIconfont.Icon.GSY_HOME)
                                .color(IconicsColor.colorInt(R.color.subTextColor))
                                .sizeDp(20))
                        .title(application.getString(R.string.searchRepos))
                        .build(),
                GSYTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .icon(GSYIconfont.Icon.GSY_REPOS_ITEM_USER)
                                .color(IconicsColor.colorInt(R.color.subTextColor))
                                .sizeDp(20))
                        .title(application.getString(R.string.searchUser))
                        .build()
        )

    }

}