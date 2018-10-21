package com.shuyu.github.kotlin.repository

import com.shuyu.github.kotlin.model.AppGlobalModel
import retrofit2.Retrofit
import javax.inject.Inject

class ReposRepository @Inject constructor(private val retrofit: Retrofit, private val appGlobalModel: AppGlobalModel) {

}