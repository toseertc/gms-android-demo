package com.kotlin.myapplication.test

import android.util.Log
import cn.tosee.gms.ErrorInfo
import cn.tosee.gms.PeerSubscriptionOption
import cn.tosee.gms.ResultCallback
import cn.tosee.gms.client.GMSClient
import cn.tosee.gms.connect.bean.GMSMessage

/*
 * Author: cqr
 * Version: V100R001C01
 * Create: 2020/6/19 14:19
 * Changes (from 2020/6/19)
 */

class PeerMsg(var createInstance: GMSClient, var userId: String) {
    val TAG = "***PeerMsg----"
    fun beginTest() {
        testqueryPeersOnlineStatus()
        testsubscribePeersOnlineStatus()
        testunsubscribePeersOnlineStatus()
        testsendMessageToPeer()
        testqueryPeersBySubscriptionOption()
    }

    fun testqueryPeersBySubscriptionOption() {
        createInstance?.queryPeersBySubscriptionOption(
            PeerSubscriptionOption.ONLINE,
            object : ResultCallback<java.util.Set<String>> {
                override fun onSuccess(responseInfo: java.util.Set<String>?) {
                    Log.e("$TAG", "testqueryPeersBySubscriptionOption begin ")
                    responseInfo?.forEach() {
                        Log.e("$TAG ", "testqueryPeersBySubscriptionOption $it ")
                    }
                }

                override fun onFailure(errorInfo: ErrorInfo) {
                    Log.e(
                        "$TAG ",
                        "testqueryPeersBySubscriptionOption failed ${errorInfo} "
                    )
                }

            })
    }

    fun testsendMessageToPeer() {
        var targetUid = "1111"
        var text = "发送给222"
        createInstance?.sendMessageToPeer(
            targetUid,
            GMSMessage(
                text,
                isOfflineMessage = true
            ), resultCallback = object : ResultCallback<Void> {
                override fun onSuccess(responseInfo: Void?) {
                    Log.e("$TAG ", "sendMessageToPeer success $responseInfo")
                }

                override fun onFailure(errorInfo: ErrorInfo) {
                    Log.e("$TAG ", "sendMessageToPeer faid ${errorInfo}")
                }

            })
    }

    fun testunsubscribePeersOnlineStatus() {
        var set: java.util.Set<String> = java.util.HashSet<String>() as java.util.Set<String>
        set.add("4")
        createInstance?.unsubscribePeersOnlineStatus(
            set,
            object : ResultCallback<Void> {
                override fun onSuccess(responseInfo: Void?) {
                    Log.e(
                        "$TAG ",
                        "testunsubscribePeersOnlineStatus success $responseInfo"
                    )
                }

                override fun onFailure(errorInfo: ErrorInfo) {
                    Log.e(
                        "$TAG ",
                        "testunsubscribePeersOnlineStatus faild ${errorInfo}"
                    )
                }

            })
    }


    fun testsubscribePeersOnlineStatus() {
        var set: java.util.Set<String> = HashSet<String>() as java.util.Set<String>
        set.add("2")
        set.add("3")
        set.add("4")
        set.add("1111")
        createInstance?.subscribePeersOnlineStatus(
            set,
            object : ResultCallback<Void> {
                override fun onSuccess(responseInfo: Void?) {
                    Log.e("$TAG ", "testsubscribePeersOnlineStatus success $responseInfo")
                }

                override fun onFailure(errorInfo: ErrorInfo) {
                    Log.e(
                        "$TAG ",
                        "testsubscribePeersOnlineStatus failure" + errorInfo.errorMsg
                    )
                }

            })
    }

    fun testqueryPeersOnlineStatus() {
        var set: java.util.Set<String> = HashSet<String>() as java.util.Set<String>
        set.add("2")
        set.add("3")
        set.add("4")
        set.add("1111")
        createInstance?.queryPeersOnlineStatus(set,
            object : ResultCallback<Map<String, Boolean>> {
                override fun onSuccess(responseInfo: Map<String, Boolean>?) {
                    Log.e("$TAG ", "queryPeersOnlineStatus success $responseInfo")
                }

                override fun onFailure(errorInfo: ErrorInfo) {
                    Log.e(
                        "$TAG ",
                        "queryPeersOnlineStatus failure" + errorInfo.errorMsg
                    )
                }
            })
    }
}