package com.shuyu.github.kotlin.di

import android.app.Application
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.mikepenz.iconics.IconicsColor
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.utils.sizeDp
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.style.GSYIconfont
import com.shuyu.github.kotlin.module.dynamic.DynamicFragment
import com.shuyu.github.kotlin.module.my.MyFragment
import com.shuyu.github.kotlin.module.trend.TrendFragment
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
        return listOf(DynamicFragment(), TrendFragment(), MyFragment())
    }

    @Provides
    fun providerMainTabModel(application: Application): List<NavigationTabBar.Model> {
        return listOf(
                NavigationTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .icon(GSYIconfont.Icon.GSY_MAIN_DT)
                                .color(IconicsColor.colorInt(R.color.subTextColor))
                                .sizeDp(20),
                        Color.parseColor("#00000000"))
                        .title(application.getString(R.string.tabDynamic))
                        .build(),
                NavigationTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .icon(GSYIconfont.Icon.GSY_MAIN_QS)
                                .color(IconicsColor.colorInt(R.color.subTextColor))
                                .sizeDp(20),
                        Color.parseColor("#00000000"))
                        .title(application.getString(R.string.tabRecommended))
                        .build(),
                NavigationTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .icon(GSYIconfont.Icon.GSY_MAIN_MY)
                                .color(IconicsColor.colorInt(R.color.subTextColor))
                                .sizeDp(20),
                        Color.parseColor("#00000000"))
                        .title(application.getString(R.string.tabMy))
                        .build()
        )

    }
}
