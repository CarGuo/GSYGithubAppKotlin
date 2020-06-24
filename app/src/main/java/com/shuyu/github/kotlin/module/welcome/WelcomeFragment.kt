package com.shuyu.github.kotlin.module.welcome

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.config.AppConfig
import com.shuyu.github.kotlin.common.net.GsonUtils
import com.shuyu.github.kotlin.common.utils.GSYPreference
import com.shuyu.github.kotlin.databinding.FragmentWelcomeBinding
import com.shuyu.github.kotlin.model.AppGlobalModel
import com.shuyu.github.kotlin.model.bean.User
import com.shuyu.github.kotlin.model.conversion.UserConversion
import com.shuyu.github.kotlin.module.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_welcome.*
import javax.inject.Inject

/**
 * 欢迎页
 * Created by guoshuyu
 * Date: 2018-09-30
 */
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>() {

    private var userInfoStorage: String by GSYPreference(AppConfig.USER_INFO, "")

    @Inject
    lateinit var appGlobalModel: AppGlobalModel

    /***
     * 委托属性，GSYPreference 把取值和存值的操作代理给 accessTokenStorage
     * 后续的赋值和取值最终是操作的 GSYPreference 得 setValue 和 getValue 函数
     */
    private var accessTokenStorage by GSYPreference(AppConfig.ACCESS_TOKEN, "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(mainView: View?) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_welcome
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ///lottie动画速度
        welcome_animation.speed = 5.0f

        //两秒后跳转下一页
        Handler().postDelayed({
            goNext(view)
        }, 2000)


    }

    private fun goNext(view: View) {
        if (accessTokenStorage.isEmpty()) {
            ///去登录页
            navigationPopUpTo(view, null, R.id.action_nav_wel_to_login, false, true)
        } else {
            if (userInfoStorage.isEmpty()) {
                ///去登录页
                navigationPopUpTo(view, null, R.id.action_nav_wel_to_login, false, true)
            } else {
                ///读取用户数据
                val user = GsonUtils.parserJsonToBean(userInfoStorage, User::class.java)
                UserConversion.cloneDataFromUser(context, user, appGlobalModel.userObservable)
                //去主页
                navigationPopUpTo(view, null, R.id.action_nav_wel_to_main, true, true)
                //navigationPopUpTo(view, null, R.id.action_nav_wel_to_login, false)
            }

        }
    }

}