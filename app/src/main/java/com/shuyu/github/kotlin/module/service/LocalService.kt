package com.shuyu.github.kotlin.module.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import com.shuyu.github.kotlin.ILocalMessage
import com.shuyu.github.kotlin.ILocalMessageCallBack
import com.shuyu.github.kotlin.common.utils.Debuger
import com.shuyu.github.kotlin.model.AIDLResultModel


/**
 * Created by guoshuyu
 * Date: 2018-11-28
 */
class LocalService : Service() {


    private var serviceMessenger: Messenger? = null
    private var currentMessenger:Messenger? = null


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Debuger.printfLog("LocalService onStartCommand  " + " pid" + android.os.Process.myPid() + " " + Thread.currentThread())
        bindService(Intent(this, LocalAIDLTransferService::class.java), connectionAidlService, BIND_AUTO_CREATE)
        bindService(Intent(this, LocalMessengerService::class.java), connectionMessengerService, BIND_AUTO_CREATE)
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        Debuger.printfLog("LocalService onCreate  " + " pid" + android.os.Process.myPid() + " " + Thread.currentThread())
    }

    override fun onBind(intent: Intent?): IBinder? {
        Debuger.printfLog("LocalService onBind  " + " pid" + android.os.Process.myPid() + " " + Thread.currentThread())
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connectionAidlService)
        unbindService(connectionMessengerService)
    }

    private val connectionAidlService = object : ServiceConnection {

        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Debuger.printfLog("LocalService onServiceConnected  " + " pid" + android.os.Process.myPid() + " " + Thread.currentThread())

            val binder = ILocalMessage.Stub.asInterface(service)
            binder.registerCallBack(resultCallBack)

            Debuger.printfLog("LocalService Binder Version " + binder.version)

            binder.sendMessage("`LocalService had connection`")


        }

    }

    private val resultCallBack = object : ILocalMessageCallBack.Stub() {

        override fun sendResult(result: AIDLResultModel?) {
            Debuger.printfLog("LocalService test code, ignore me please.")
            Debuger.printfLog("LocalService getResult  " + result.toString() + " pid" + android.os.Process.myPid() + " " + Thread.currentThread())
        }
    }


    private val connectionMessengerService = object : ServiceConnection {

        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            serviceMessenger = Messenger(service)
            val msgFromClient = Message.obtain(null, 0, 1, 1)
            Debuger.printfLog("LocalService send Messenger " + msgFromClient.arg1 + " pid" + android.os.Process.myPid() + " " + Thread.currentThread())
            currentMessenger = Messenger(messageHandler)
            msgFromClient.replyTo = currentMessenger
            serviceMessenger?.send(msgFromClient)
        }

    }

    @SuppressLint("HandlerLeak")
    private val messageHandler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                0 -> {
                    //这里因为是在Service中创建的，所以打印出来的是主进程的主线程
                    //所以这里如果要在回复Messenger到MessengerService的话，最好是在新线程中发送
                    Debuger.printfLog("LocalService handleMessage " + msg.arg1 + " pid" + android.os.Process.myPid() + " " + Thread.currentThread())
                }
            }
            super.handleMessage(msg)
        }
    }
}

/**
 * LocalService                onCreate                                   pid4442 Thread[main,5,main]（主进程主线程）
 * LocalService                onStartCommand                             pid4442 Thread[main,5,main]（主进程主线程）
 * LocalAIDLTransferService    onCreate                                   pid4347 Thread[main,5,main]（新进程主线程）
 * LocalAIDLTransferService    onBind                                     pid4347 Thread[main,5,main]（新进程主线程）
 * LocalService                onServiceConnected                         pid4442 Thread[main,5,main]（主进程主线程）
 * LocalService                Binder Version 1
 * LocalAIDLTransferService    sendMessage `LocalService had connection`  pid4347 Thread[Binder:4347_1,5,main]
 */

/**
 * LocalAIDLTransferService    send Result                                                                       pid8308 Thread[pool-4-thread-1,5,main]
 * LocalService                test code, ignore me please.
 * LocalService                getResult  name : Name 9e439268-5bd9-470c-9d7e-9538bd3f98a4 time : 1543391187009  pid8349 Thread[Binder:8349_2,5,main]
 * */


/**
 * LocalService                  send Messenger 1     pid9582 Thread[main,5,main]（主进程主线程）
 * LocalMessengerService         handleMessage  1     pid9537 Thread[main,5,main]
 * LocalMessengerService         send Messenger 2     pid9537 Thread[main,5,main]
 * LocalService                  handleMessage  2     pid9582 Thread[main,5,main]（主进程主线程）
 *
 *
 *
 **/