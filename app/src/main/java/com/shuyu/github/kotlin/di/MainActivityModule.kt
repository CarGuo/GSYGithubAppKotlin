package com.shuyu.github.kotlin.di

import android.app.Application
import androidx.fragment.app.Fragment
import com.mikepenz.iconics.IconicsDrawable
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.style.GSYIconfont
import com.shuyu.github.kotlin.common.style.applyIconAndColor
import com.shuyu.github.kotlin.module.dynamic.DynamicFragment
import com.shuyu.github.kotlin.module.my.MyFragment
import com.shuyu.github.kotlin.module.trend.TrendFragment
import com.shuyu.github.kotlin.ui.view.GSYTabBar
import dagger.Module
import dagger.Provides

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
    fun providerMainTabModel(application: Application): List<GSYTabBar.Model> {
        return listOf(
                GSYTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .applyIconAndColor(GSYIconfont.Icon.GSY_MAIN_DT, R.color.subTextColor, 20))
                        .title(application.getString(R.string.tabDynamic))
                        .build(),
                GSYTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .applyIconAndColor(GSYIconfont.Icon.GSY_MAIN_QS, R.color.subTextColor, 20))
                        .title(application.getString(R.string.tabRecommended))
                        .build(),
                GSYTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .applyIconAndColor(GSYIconfont.Icon.GSY_MAIN_MY, R.color.subTextColor, 20))
                        .title(application.getString(R.string.tabMy))
                        .build()
        )

    }
}
