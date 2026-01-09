package com.shuyu.github.kotlin.di

import android.app.Application
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.ui.view.GSYTabBar
import dagger.Module
import dagger.Provides

/**
 * 仓库详情TabBar数据
 * Created by guoshuyu
 * Date: 2018-10-25
 */

@Module
class ReposDetailModule {
    @Provides
    fun providerMainTabModel(application: Application): List<GSYTabBar.Model> {
        return listOf(
                GSYTabBar.Model.Builder(null)
                        .title(application.getString(R.string.reposReadme))
                        .build(),
                GSYTabBar.Model.Builder(null)
                        .title(application.getString(R.string.reposActivity))
                        .build(),
                GSYTabBar.Model.Builder(null)
                        .title(application.getString(R.string.reposFile))
                        .build(),
                GSYTabBar.Model.Builder(null)
                        .title(application.getString(R.string.reposIssue))
                        .build()
        )

    }
}