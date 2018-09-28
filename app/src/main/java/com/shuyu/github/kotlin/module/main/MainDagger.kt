package com.shuyu.github.kotlin.module.main

import android.support.v4.app.Fragment
import com.shuyu.github.kotlin.module.main.dynamic.DynamicFragment
import dagger.Component
import dagger.Module
import dagger.Provides

/**
 * Created by guoshuyu
 * Date: 2018-09-28
 */



@Module
class MainProviderModule {
    @Provides
    fun providerMainFragmentList() :List<Fragment> {
        return listOf(DynamicFragment(), DynamicFragment(), DynamicFragment())
    }
}

@Component(modules = [MainProviderModule::class])
interface MainAcitivityComponent {
    fun inject(activity: MainActivity)
}
