package com.shuyu.github.kotlin.module.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.shuyu.github.kotlin.ILocalMessage
import com.shuyu.github.kotlin.common.utils.Debuger

/**
 * Created by guoshuyu
 * Date: 2018-11-28
 */
class LocalMessageService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Debuger.printfLog("LocalMessageService onStartCommand " + " pid"  + android.os.Process.myPid() + " " + Thread.currentThread())
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        Debuger.printfLog("LocalMessageService onCreate " + " pid"  + android.os.Process.myPid() + " " + Thread.currentThread())
    }

    override fun onBind(intent: Intent?): IBinder? {
        Debuger.printfLog("LocalMessageService onBind " + " pid"  + android.os.Process.myPid() + " " + Thread.currentThread())
        return binder
    }

    private val binder = object :ILocalMessage.Stub() {
        override fun sendMessage(message: String?) {
            Debuger.printfLog("LocalMessageService sendMessage $message " + " pid"  + android.os.Process.myPid() + " " + Thread.currentThread())
        }

        override fun getVersion(): Int {
            return 1
        }
    }
}