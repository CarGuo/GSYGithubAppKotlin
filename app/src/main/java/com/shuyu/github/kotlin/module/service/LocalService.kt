package com.shuyu.github.kotlin.module.service

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.shuyu.github.kotlin.ILocalMessage
import com.shuyu.github.kotlin.common.utils.Debuger

/**
 * Created by guoshuyu
 * Date: 2018-11-28
 */
class LocalService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Debuger.printfLog("LocalService onStartCommand  " + " pid"  + android.os.Process.myPid() + " " + Thread.currentThread())
        bindService(Intent(this, LocalMessageService::class.java), connection, BIND_AUTO_CREATE)
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        Debuger.printfLog("LocalService onCreate  " + " pid"  + android.os.Process.myPid() + " " + Thread.currentThread())
    }

    override fun onBind(intent: Intent?): IBinder? {
        Debuger.printfLog("LocalService onBind  " + " pid"  + android.os.Process.myPid() + " " + Thread.currentThread())
        return null
    }

    private val connection = object : ServiceConnection {

        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Debuger.printfLog("LocalService onServiceConnected  " + " pid"  + android.os.Process.myPid() + " " + Thread.currentThread())

            val binder = ILocalMessage.Stub.asInterface(service)

            Debuger.printfLog("LocalService Binder Version " + binder.version)

            binder.sendMessage("`LocalService had connection`")

        }

    }
}

/**
 * LocalService           onCreate                                   pid4442 Thread[main,5,main]
 * LocalService           onStartCommand                             pid4442 Thread[main,5,main]
 * LocalMessageService    onCreate                                   pid4347 Thread[main,5,main]
 * LocalMessageService    onBind                                     pid4347 Thread[main,5,main]
 * LocalService           onServiceConnected                         pid4442 Thread[main,5,main]
 * LocalService           Binder Version 1
 * LocalMessageService    sendMessage `LocalService had connection`  pid4347 Thread[Binder:4347_1,5,main]
 *
 * */