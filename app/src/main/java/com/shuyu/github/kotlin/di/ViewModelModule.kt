/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.shuyu.github.kotlin.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.shuyu.github.kotlin.GSYViewModelFactory
import com.shuyu.github.kotlin.di.annotation.ViewModelKey
import com.shuyu.github.kotlin.module.dynamic.DynamicViewModel
import com.shuyu.github.kotlin.module.login.LoginViewModel
import com.shuyu.github.kotlin.module.my.MyViewModel
import com.shuyu.github.kotlin.module.trend.TrendViewModel


import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * ViewModel的注入
 */
@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DynamicViewModel::class)
    abstract fun bindDynamicViewModel(dynamicViewModel: DynamicViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(TrendViewModel::class)
    abstract fun bindTrendViewModel(trendViewModel: TrendViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(MyViewModel::class)
    abstract fun bindMyViewModel(myViewModel: MyViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: GSYViewModelFactory): ViewModelProvider.Factory
}
