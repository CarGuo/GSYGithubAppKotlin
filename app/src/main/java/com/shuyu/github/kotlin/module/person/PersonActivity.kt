package com.shuyu.github.kotlin.module.person

import android.content.Context
import android.support.v4.app.Fragment
import com.shuyu.github.kotlin.module.base.BaseFragmentActivity
import com.shuyu.github.kotlin.module.base.startActivity

/**
 * Created by guoshuyu
 * Date: 2018-10-24
 */

class PersonActivity : BaseFragmentActivity() {

    companion object {
        fun gotoPersonInfo(context: Context) {
            context.startActivity(PersonActivity::class.java)
        }
    }

    override fun getInitFragment(): Fragment {
        return PersonFragment()
    }
}