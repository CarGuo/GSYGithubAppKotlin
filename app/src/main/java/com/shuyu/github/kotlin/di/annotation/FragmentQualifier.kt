package com.shuyu.github.kotlin.di.annotation

import javax.inject.Qualifier



/**
 * Created by guoshuyu
 * Date: 2018-11-02
 */

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentQualifier (val value: String = "")
