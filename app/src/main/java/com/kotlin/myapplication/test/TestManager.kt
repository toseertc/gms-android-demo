package com.duobeiyun.generamessagedemo.test

import android.content.Context
import android.util.Log
import com.duobeiyun.generamessagedemo.test.invitee.Invitee
import com.duobeiyun.generamessagesdk.ErrorInfo
import com.duobeiyun.generamessagesdk.ResultCallback
import com.duobeiyun.generamessagesdk.client.GmsClient

/*
 * Author: cqr
 * Version: V100R001C01
 * Create: 2020/6/19 14:12
 * Changes (from 2020/6/19)
 */
fun main() {
    run stop@{
        mutableListOf<String>("1", "2", "3", "2", "4", "5", "6")?.forEach {
            if (it.equals("2")) {
                return@stop
            }
            println("---$it")
        }
    }
    println("--end")
}

class TestManager(var createInstance: GmsClient, var userId: String, var context: Context) {
    private fun testLogin() {
        createInstance?.login(userId!!,
            object : ResultCallback<Void> {
                override fun onSuccess(responseInfo: Void?) {
                    Log.e("MainActivity", "login success")
                    allTestCases()
                    Log.e("MainActivity", "end Test")
//        testLogout()
                }

                override fun onFailure(errorInfo: ErrorInfo) {
                    Log.e("MainActivity", "login failure：" + errorInfo.errorMsg)
                    allTestCases()
                }
            })
    }

    lateinit var chanel: Chanel;
    lateinit var localAttr: LocalUser;
    lateinit var peerMsg: PeerMsg;
    lateinit var inviter: Inviter;
    lateinit var invitee: Invitee;
    var TestAsInviter = true
    private fun initAllTest() {
        if (::inviter.isInitialized) {
            return
        }
        chanel = Chanel(createInstance, userId)
        localAttr = LocalUser(createInstance, userId)
        peerMsg = PeerMsg(createInstance, userId)
        inviter = Inviter(createInstance, userId, context)
        invitee = Invitee(context, createInstance)
    }

    private fun allTestCases() {
        initAllTest();
        //频道
        chanel.beginTest()
//属性
        localAttr.beginTest()
        // peer msg
        peerMsg.beginTest()
        //呼叫功能
        if (TestAsInviter) {
            inviter.beginTest()
        } else {
            invitee.beginTest()
        }
        //网络模块
        var wifiTest = WifiTest(createInstance, userId)
        wifiTest.beginTest()
    }

    fun beginTest() {
        Log.e("MainActivity", "begin Test")
        testLogin();
    }

    private fun testLogout() {
        createInstance?.logout(object : ResultCallback<Void> {
            override fun onSuccess(responseInfo: Void?) {

            }

            override fun onFailure(errorInfo: ErrorInfo) {
            }
        })
    }
}