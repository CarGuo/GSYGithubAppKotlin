package com.shuyu.github.kotlin.di

import android.app.Application
import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.mikepenz.iconics.IconicsDrawable
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.style.GSYIconfont
import com.shuyu.github.kotlin.module.dynamic.DynamicFragment
import dagger.Module
import dagger.Provides
import devlight.io.library.ntb.NavigationTabBar

/**
 * MainActivity注入需要的Module
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
                                .color(ContextCompat.getColor(application, R.color.subTextColor))
                                .sizeDp(20),
                        Color.parseColor("#00000000"))
                        .title(application.getString(R.string.tabDynamic))
                        .build(),
                NavigationTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .icon(GSYIconfont.Icon.GSY_MAIN_QS)
                                .color(ContextCompat.getColor(application, R.color.subTextColor))
                                .sizeDp(20),
                        Color.parseColor("#00000000"))
                        .title(application.getString(R.string.tabRecommended))
                        .build(),
                NavigationTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .icon(GSYIconfont.Icon.GSY_MAIN_MY)
                                .color(ContextCompat.getColor(application, R.color.subTextColor))
                                .sizeDp(20),
                        Color.parseColor("#00000000"))
                        .title(application.getString(R.string.tabMy))
                        .build()
        )

    }
}
