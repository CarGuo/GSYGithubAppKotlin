package com.shuyu.github.kotlin.di

import android.app.Application
import android.support.v4.content.ContextCompat
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.style.GSYIconfont
import com.shuyu.github.kotlin.di.annotation.FragmentQualifier
import dagger.Module
import dagger.Provides
import devlight.io.library.ntb.NavigationTabBar

/**
 * Created by guoshuyu
 * Date: 2018-10-25
 */

@Module
class SearchModule {


    @FragmentQualifier("Search")
    @Provides
    fun providerReposIssueListTabModel(application: Application): List<NavigationTabBar.Model> {
        return listOf(
                NavigationTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .icon(GSYIconfont.Icon.GSY_HOME)
                                .color(ContextCompat.getColor(application, R.color.subTextColor))
                                .sizeDp(20),
                        ContextCompat.getColor(application, R.color.colorPrimaryLight))
                        .title(application.getString(R.string.searchRepos))
                        .build(),
                NavigationTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .icon(GSYIconfont.Icon.GSY_REPOS_ITEM_USER)
                                .color(ContextCompat.getColor(application, R.color.subTextColor))
                                .sizeDp(20),
                        ContextCompat.getColor(application, R.color.colorPrimaryLight))
                        .title(application.getString(R.string.searchUser))
                        .build()
        )

    }

}