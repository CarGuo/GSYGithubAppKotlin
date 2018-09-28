package com.shuyu.github.kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.shuyu.github.kotlin.common.net.ResultObserver
import com.shuyu.github.kotlin.common.net.RetrofitFactory
import com.shuyu.github.kotlin.model.AccessToken
import com.shuyu.github.kotlin.model.LoginRequestModel
import com.shuyu.github.kotlin.service.LoginService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        test.setOnClickListener {
            val authorization = RetrofitFactory.createService(LoginService::class.java).authorizations(LoginRequestModel.generate())
            RetrofitFactory.executeResult(authorization, object : ResultObserver<AccessToken>() {
                override fun onSuccess(t: AccessToken?) {

                }

                override fun onFailure(e: Throwable, isNetWorkError: Boolean) {

                }
            })
        }
    }
}
