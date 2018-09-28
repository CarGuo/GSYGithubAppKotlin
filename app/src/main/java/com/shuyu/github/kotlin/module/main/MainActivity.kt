package com.shuyu.github.kotlin.module.main
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.ui.adapter.FragmentPagerViewAdapter
import devlight.io.library.ntb.NavigationTabBar
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    /**
     * fragment列表
     */
    @Inject
    lateinit var mainFragmentList:MutableList<Fragment>

    /**
     * tab列表
     */
    @Inject
    lateinit var mainTabModel:MutableList<NavigationTabBar.Model>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        DaggerMainActivityComponent.builder()
                .mainProviderModule(MainProviderModule(applicationContext))
                .build()
                .inject(this)

        home_view_pager.adapter = FragmentPagerViewAdapter(mainFragmentList, supportFragmentManager)
        home_navigation_tab_bar.models = mainTabModel
        home_navigation_tab_bar.setViewPager(home_view_pager, 0)

        /*test.setOnClickListener {
            val authorization = RetrofitFactory.createService(LoginService::class.java).authorizations(LoginRequestModel.generate())
            RetrofitFactory.executeResult(authorization, object : ResultObserver<AccessToken>() {
                override fun onSuccess(t: AccessToken?) {

                }

                override fun onFailure(e: Throwable, isNetWorkError: Boolean) {

                }
            })
        }*/
    }
}
