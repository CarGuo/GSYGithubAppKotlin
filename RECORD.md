




目前已知问题

* 下拉刷新效果的触摸滑动，需要优化滑动问题

1.0.0 欠缺

repository action的提交列表

主页drawer（个人信息）

图片预览（readme 和文件下的图片预览）

提交页面详情、提交对比详情、提交选择列表

1.0.1

空页面显示

readme 页面跳转拦截（ARouter？）

repository action 的提交列表

push 弹出选择框 和 push详情

通知消息列表

用户相关列表 和 仓库相关列表

release 列表


1.0.2

数据库

显示用户所在组织

检查更新

repository action tag 显示

issue 数据统计

图片预览和保存


未来









































## android-architecture-components

### BasicSample

* BasicApp 

创建了 `AppExecutors` 线程池和 `DataRepository` 数据仓库

* DataRepository

数据仓库，持有 `AppDatabase`  和 `MediatorLiveData`Products

* ProductListFragment

1、 创建 `DataBinding`  mBinding 。
2、通过 `ProductListViewModel.Factory`  创建 `ProductListViewModel`。
3、订阅 vm 内的 `LiveData`、用于更新 DataBinding 的 `ObservableField` 和手动更新数据。
4、`ProductAdapter` 中通过 `DiffUtil` 实现更新，通过  `DataBinding` 同步数据

* ProductFragment

1、创建 `DataBinding`  mBinding 。
2、通过 `ProductViewModel.Factory`  创建 `ProductViewModel`。
3、将 `ProductViewModel` 设置给 mBinding。
4、订阅 vm 内的 `LiveData`、用于更新 DataBinding 的 `ObservableField` 和手动更新数据。

* ProductListViewModel

持有 `Repository` 和 `LiveData`，用于请求和对外提供LiveData订阅。

### GithubBrowserSample

* GithubApp（ Application）

1、初始化 `AppInjector.init()` 
2、注入 `DispatchingAndroidInjector`，实现 `HasActivityInjector` 接口

* AppInjector

1、调用 `DaggerAppComponent` 实现 Application 的 `inject`
2、注册 `registerActivityLifecycleCallbacks`，判断 Activity 和 Fragment是否有需要` AndroidInjection.inject(activity)`

* DaggerAppComponent

由 `AppComponent` 、 `AppModule`、 `MainActivityModule` 、`AndroidInjectionModule` 配合注入。

1、AndroidInjectionModule 

*This module should be installed in the component that is used to inject the {android.app.Application} class.*

你还可以用 `AndroidSupportInjectionModule` 可以额外支持V4包下的`Fragment`

2、MainActivityModule

*@ContributesAndroidInjector(modules = [FragmentBuildersModule::class])*

理解上是替换了原本 MainActivity注入需要的Component，在 MainActivityModule 中通过 `ContributesAndroidInjector` 并增加需要的其他Module(对Activity内的Fragmentmodule提供注入)，实现MainActivity的注入：`AndroidInjection.inject(activity)`。

3、AppModule

*提供 Service、Db、Dao的单例注入，依赖 `ViewModelModule`，
ViewModelModule 是 VM相关的*

4、AppComponent 

*通过 @Component.Builder 增加builder方法，提供Application 注入方法。*

引用上述所有Module





### ViewModel + Dagger2

https://blog.csdn.net/qq_30889373/article/details/81806129






# Naviagiton：



目前需要NavHostFragment，配置后可以Fragment 、Activity之间跳转。不过Fragment - Activity 是跳出了 graph，在新的Activity内可以设置新的graph

https://blog.csdn.net/cekiasoo/article/details/80739805

https://blog.csdn.net/weixin_42215792/article/details/80326637