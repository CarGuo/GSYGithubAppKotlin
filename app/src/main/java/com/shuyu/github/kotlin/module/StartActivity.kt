package com.shuyu.github.kotlin.module

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.shuyu.github.kotlin.R
import org.jetbrains.anko.clearTask

/**
 * Created by guoshuyu
 * Date: 2018-09-30
 */
class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val intent = Intent(this, StartNavigationActivity::class.java)
        intent.clearTask()
        startActivity(intent)

        finish()

    }
}