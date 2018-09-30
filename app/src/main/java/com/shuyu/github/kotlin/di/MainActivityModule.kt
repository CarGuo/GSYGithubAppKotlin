package com.shuyu.github.kotlin.di

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.support.v4.app.Fragment
import com.mikepenz.iconics.IconicsDrawable
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.style.GSYIconfont
import com.shuyu.github.kotlin.module.main.MainActivity
import com.shuyu.github.kotlin.module.main.dynamic.DynamicFragment
import dagger.Component
import dagger.Module
import dagger.Provides
import devlight.io.library.ntb.NavigationTabBar

/**
 * Created by guoshuyu
 * Date: 2018-09-28
 */


@Module
class MainActivityModule {

    @Provides
    fun providerMainFragmentList(): List<Fragment> {
        return listOf(DynamicFragment(), DynamicFragment(), DynamicFragment())
    }

    @Provides
    fun providerMainTabModel(application: Application): List<NavigationTabBar.Model> {
        return listOf(
                NavigationTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .icon(GSYIconfont.Icon.GSY_MAIN_DT)
                                .color(Color.RED)
                                .sizeDp(20),
                        Color.parseColor("#00000000"))
                        .title(application.getString(R.string.tabDynamic))
                        .build(),
                NavigationTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .icon(GSYIconfont.Icon.GSY_MAIN_QS)
                                .color(Color.RED)
                                .sizeDp(20),
                        Color.parseColor("#00000000"))
                        .title(application.getString(R.string.tabRecommended))
                        .build(),
                NavigationTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .icon(GSYIconfont.Icon.GSY_MAIN_MY)
                                .color(Color.RED)
                                .sizeDp(20),
                        Color.parseColor("#00000000"))
                        .title(application.getString(R.string.tabMy))
                        .build()
        )

    }
}
