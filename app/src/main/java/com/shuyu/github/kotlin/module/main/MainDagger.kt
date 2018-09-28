package com.shuyu.github.kotlin.module.main

import android.content.Context
import android.graphics.Color
import android.support.v4.app.Fragment
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.iconics.IconicsDrawable
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.style.GSYIconfont
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
class MainProviderModule() {

    private lateinit var context: Context

    constructor(context: Context) : this() {
        this.context = context
    }


    @Provides
    fun providerMainFragmentList(): List<Fragment> {
        return listOf(DynamicFragment(), DynamicFragment(), DynamicFragment())
    }

    @Provides
    fun providerMainTabModel(): List<NavigationTabBar.Model> {
        return listOf(
                NavigationTabBar.Model.Builder(
                        IconicsDrawable(context)
                                .icon(GSYIconfont.Icon.MAIN_DT)
                                .color(Color.RED)
                                .sizeDp(20),
                        Color.parseColor("#00000000"))
                        .title(context.getString(R.string.tabDynamic))
                        .build(),
                NavigationTabBar.Model.Builder(
                        IconicsDrawable(context)
                                .icon(GSYIconfont.Icon.MAIN_QS)
                                .color(Color.RED)
                                .sizeDp(20),
                        Color.parseColor("#00000000"))
                        .title(context.getString(R.string.tabRecommended))
                        .build(),
                NavigationTabBar.Model.Builder(
                        IconicsDrawable(context)
                                .icon(GSYIconfont.Icon.MAIN_MY)
                                .color(Color.RED)
                                .sizeDp(20),
                        Color.parseColor("#00000000"))
                        .title(context.getString(R.string.tabMy))
                        .build()
        )

    }
}

@Component(modules = [MainProviderModule::class])
interface MainActivityComponent {
    fun inject(activity: MainActivity)
}
