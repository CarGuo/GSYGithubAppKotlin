package com.shuyu.github.kotlin.di

import android.app.Application
import android.graphics.Color
import com.shuyu.github.kotlin.R
import dagger.Module
import dagger.Provides
import devlight.io.library.ntb.NavigationTabBar

/**
 * Created by guoshuyu
 * Date: 2018-10-25
 */

@Module
class ReposDetailModule {
    @Provides
    fun providerMainTabModel(application: Application): List<NavigationTabBar.Model> {
        return listOf(
                NavigationTabBar.Model.Builder(null,
                        Color.parseColor("#00000000"))
                        .title(application.getString(R.string.reposReadme))
                        .build(),
                NavigationTabBar.Model.Builder(null,
                        Color.parseColor("#00000000"))
                        .title(application.getString(R.string.reposActivity))
                        .build(),
                NavigationTabBar.Model.Builder(null,
                        Color.parseColor("#00000000"))
                        .title(application.getString(R.string.reposFile))
                        .build(),
                NavigationTabBar.Model.Builder(null,
                        Color.parseColor("#00000000"))
                        .title(application.getString(R.string.reposIssue))
                        .build()
        )

    }
}