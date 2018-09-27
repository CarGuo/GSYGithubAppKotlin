package com.shuyu.github.kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.shuyu.github.kotlin.common.net.ResultObserver
import com.shuyu.github.kotlin.common.net.RetrofitFactory
import com.shuyu.github.kotlin.model.AccessToken
import com.shuyu.github.kotlin.model.LoginRequestModel
import com.shuyu.github.kotlin.service.LoginService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        test.setOnClickListener {
            RetrofitFactory.instence!!.retrofit.create(LoginService::class.java)
                    .authorizations(LoginRequestModel.generate())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : ResultObserver<AccessToken>() {
                        override fun onSuccess(t: AccessToken?) {
                        }

                        override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                        }
                    })
        }
    }
}
