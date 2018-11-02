package com.shuyu.github.kotlin.di

import android.app.Application
import android.graphics.Color
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.di.annotation.FragmentQualifier
import dagger.Module
import dagger.Provides
import devlight.io.library.ntb.NavigationTabBar

/**
 * Created by guoshuyu
 * Date: 2018-10-25
 */

@Module
class ReposIssueListModule {

    
    @FragmentQualifier("IssueList")
    @Provides
    fun providerReposIssueListTabModel(application: Application): List<NavigationTabBar.Model> {
        return listOf(
                NavigationTabBar.Model.Builder(null,
                        Color.parseColor("#00000000"))
                        .title(application.getString(R.string.issueAllText))
                        .build(),
                NavigationTabBar.Model.Builder(null,
                        Color.parseColor("#00000000"))
                        .title(application.getString(R.string.issueOpenText))
                        .build(),
                NavigationTabBar.Model.Builder(null,
                        Color.parseColor("#00000000"))
                        .title(application.getString(R.string.issueCloseText))
                        .build()
        )

    }
}