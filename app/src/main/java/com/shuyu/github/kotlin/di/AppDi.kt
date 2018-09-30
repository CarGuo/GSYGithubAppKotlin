package com.shuyu.github.kotlin.di

import android.app.Application
import com.shuyu.github.kotlin.GSYGithubApplication
import com.shuyu.github.kotlin.common.net.RetrofitFactory
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by guoshuyu
 * Date: 2018-09-30
 */

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityBindModule::class
])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(gsyGithubApplication: GSYGithubApplication)
}

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun providerRetrofit(): Retrofit {
        return RetrofitFactory.instance.retrofit
    }
}



