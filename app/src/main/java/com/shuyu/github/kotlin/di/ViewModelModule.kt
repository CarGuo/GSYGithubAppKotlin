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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shuyu.github.kotlin.GSYViewModelFactory
import com.shuyu.github.kotlin.di.annotation.ViewModelKey
import com.shuyu.github.kotlin.module.code.CodeDetailViewModel
import com.shuyu.github.kotlin.module.dynamic.DynamicViewModel
import com.shuyu.github.kotlin.module.info.UserInfoViewModel
import com.shuyu.github.kotlin.module.issue.IssueDetailViewModel
import com.shuyu.github.kotlin.module.list.GeneralListViewModel
import com.shuyu.github.kotlin.module.login.LoginViewModel
import com.shuyu.github.kotlin.module.my.MyViewModel
import com.shuyu.github.kotlin.module.notify.NotifyViewModel
import com.shuyu.github.kotlin.module.person.PersonViewModel
import com.shuyu.github.kotlin.module.push.PushDetailViewModel
import com.shuyu.github.kotlin.module.repos.ReposDetailViewModel
import com.shuyu.github.kotlin.module.repos.action.ReposActionViewModel
import com.shuyu.github.kotlin.module.repos.file.ReposFileViewModel
import com.shuyu.github.kotlin.module.repos.issue.ReposIssueListViewModel
import com.shuyu.github.kotlin.module.repos.readme.ReposReadmeViewModel
import com.shuyu.github.kotlin.module.search.SearchViewModel
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

    // @Binds 类似于 @Provides，在使用接口声明时使用，区别是 @Binds 用于修饰抽象类中的抽象方法的
    // 这个方法必须返回接口或抽象类，比如 ViewModel，不能直接返回 LoginViewModel
    // 方法的参数就是这个方法返回的是注入的对象，类似@Provides修饰的方法返回的对象
    // 这里的 LoginViewModel 会通过上述声明的构造器注入自动构建
    @Binds
    @IntoMap
    //@MapKey的封装注解
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    // Dagger不仅可以将绑定的多个元素依赖注入到Set，还可以将绑定的多个元素依赖注入到Map。
    // 与依赖注入Set不同的是，依赖注入Map时，必须在编译时指定Map的Key，那么Dagger向MapMap注入相应的元素
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
    @IntoMap
    @ViewModelKey(PersonViewModel::class)
    abstract fun bindPersonViewModel(personViewModel: PersonViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReposReadmeViewModel::class)
    abstract fun bindReposReadmeViewModel(reposReadmeViewModel: ReposReadmeViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(ReposActionViewModel::class)
    abstract fun bindReposActionViewModel(reposActionViewModel: ReposActionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReposFileViewModel::class)
    abstract fun bindReposFileViewModel(reposFileViewModel: ReposFileViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(CodeDetailViewModel::class)
    abstract fun bindCodeDetailViewModel(codeDetailViewModel: CodeDetailViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(ReposDetailViewModel::class)
    abstract fun bindReposDetailViewModel(reposDetailViewModel: ReposDetailViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(ReposIssueListViewModel::class)
    abstract fun bindReposIssueListViewModel(reposIssueListViewModel: ReposIssueListViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(IssueDetailViewModel::class)
    abstract fun bindIssueDetailViewModel(issueDetailViewModel: IssueDetailViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindIssueSearchViewModel(searchViewModel: SearchViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(GeneralListViewModel::class)
    abstract fun bindGeneralListViewModel(generalListViewModel: GeneralListViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(NotifyViewModel::class)
    abstract fun bindNotifyViewModel(notifyViewModel: NotifyViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PushDetailViewModel::class)
    abstract fun bindPushDetailViewModel(pushDetailViewModel: PushDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserInfoViewModel::class)
    abstract fun bindUserInfoViewModel(userInfoViewModel: UserInfoViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: GSYViewModelFactory): ViewModelProvider.Factory
}
