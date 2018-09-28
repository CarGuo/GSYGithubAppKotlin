package com.shuyu.github.kotlin.ui

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.net.ResultObserver
import com.shuyu.github.kotlin.common.net.RetrofitFactory
import com.shuyu.github.kotlin.model.AccessToken
import com.shuyu.github.kotlin.model.LoginRequestModel
import com.shuyu.github.kotlin.service.LoginService
import com.shuyu.github.kotlin.ui.adapter.FragmentPagerViewAdapter
import com.shuyu.github.kotlin.ui.fragment.DynamicFragment
import devlight.io.library.ntb.NavigationTabBar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val list = listOf(DynamicFragment(), DynamicFragment(), DynamicFragment())

        home_view_pager.adapter = FragmentPagerViewAdapter(list, supportFragmentManager)


        val colors = resources.getStringArray(R.array.default_preview)

        val models = ArrayList<NavigationTabBar.Model>()
        models.add(
                NavigationTabBar.Model.Builder(
                        resources.getDrawable(R.drawable.ic_launcher_background),
                        Color.parseColor(colors[0]))
                        .selectedIcon(resources.getDrawable(R.drawable.ic_launcher_background))
                        .title("Heart")
                        .badgeTitle("NTB")
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        resources.getDrawable(R.drawable.ic_launcher_background),
                        Color.parseColor(colors[1]))
                        //                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Cup")
                        .badgeTitle("with")
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        resources.getDrawable(R.drawable.ic_launcher_background),
                        Color.parseColor(colors[2]))
                        .selectedIcon(resources.getDrawable(R.drawable.ic_launcher_background))
                        .title("Diploma")
                        .badgeTitle("state")
                        .build()
        )

        home_navigation_tab_bar.models = models
        home_navigation_tab_bar.setViewPager(home_view_pager, 2)
        home_navigation_tab_bar.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                home_navigation_tab_bar.models[position].hideBadge()
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
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
