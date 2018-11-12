package com.shuyu.github.kotlin.di

import android.app.Application
import android.graphics.Color
import com.shuyu.github.kotlin.R
import dagger.Module
import dagger.Provides
import devlight.io.library.ntb.NavigationTabBar

/**
 * 仓库详情TabBar数据
 * Created by guoshuyu
 * Date: 2018-10-25
 */

@Module
class NotifyModule {

    @Provides
    fun providerNotifyTabModel(application: Application): List<NavigationTabBar.Model> {
        return listOf(
                NavigationTabBar.Model.Builder(null,
                        Color.parseColor("#00000000"))
                        .title(application.getString(R.string.notifyUnread))
                        .build(),
                NavigationTabBar.Model.Builder(null,
                        Color.parseColor("#00000000"))
                        .title(application.getString(R.string.notifyParticipating))
                        .build(),
                NavigationTabBar.Model.Builder(null,
                        Color.parseColor("#00000000"))
                        .title(application.getString(R.string.notifyAll))
                        .build()
        )

    }
}