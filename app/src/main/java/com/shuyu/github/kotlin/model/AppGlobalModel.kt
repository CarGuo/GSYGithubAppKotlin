package com.shuyu.github.kotlin.model

import android.databinding.ObservableField
import com.shuyu.github.kotlin.model.bean.User
import javax.inject.Inject
import javax.inject.Singleton

/**
 * app全局数据对象
 * Created by guoshuyu
 * Date: 2018-10-19
 */
@Singleton
class AppGlobalModel @Inject constructor() {
    val userObservable = ObservableField<User>()
}