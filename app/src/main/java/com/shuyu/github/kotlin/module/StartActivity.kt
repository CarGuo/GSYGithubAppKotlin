package com.shuyu.github.kotlin.module

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shuyu.github.kotlin.R
import org.jetbrains.anko.clearTask

/**
 * 启动页
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

    override fun onBackPressed() {

    }
}