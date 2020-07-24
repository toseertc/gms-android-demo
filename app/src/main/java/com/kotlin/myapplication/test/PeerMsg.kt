package com.duobeiyun.generamessagedemo.test

import android.util.Log
import com.duobeiyun.generamessagesdk.ErrorInfo
import com.duobeiyun.generamessagesdk.ResultCallback
import com.duobeiyun.generamessagesdk.client.GmsClient
import com.duobeiyun.generamessagesdk.connect.bean.GmsMessage

/*
 * Author: cqr
 * Version: V100R001C01
 * Create: 2020/6/19 14:19
 * Changes (from 2020/6/19)
 */

class PeerMsg(var createInstance: GmsClient, var userId: String) {
    val TAG = "PeerMsg"
    fun beginTest() {
        testqueryPeersOnlineStatus()
        testsubscribePeersOnlineStatus()
        testunsubscribePeersOnlineStatus()
        testsendMessageToPeer()
        testqueryPeersBySubscriptionOption()
    }

    fun testqueryPeersBySubscriptionOption() {
        createInstance?.queryPeersBySubscriptionOption(
            0,
            object : ResultCallback<java.util.Set<String>> {
                override fun onSuccess(responseInfo: java.util.Set<String>?) {
                    Log.e("$TAG MainActivity", "testqueryPeersBySubscriptionOption begin ")
                    responseInfo?.forEach() {
                        Log.e("$TAG MainActivity", "testqueryPeersBySubscriptionOption $it ")
                    }
                }

                override fun onFailure(errorInfo: ErrorInfo) {
                    Log.e(
                        "$TAG MainActivity",
                        "testqueryPeersBySubscriptionOption failed ${errorInfo} "
                    )
                }

            })
    }

    fun testsendMessageToPeer() {
        var targetUid = userId
        var text = "发送给222"
        createInstance?.sendMessageToPeer(
            GmsMessage(
                text,
                targetUserId = targetUid,
                isOfflineMessage = true
            ), object : ResultCallback<Void> {
                override fun onSuccess(responseInfo: Void?) {
                    Log.e("$TAG MainActivity", "sendMessageToPeer success $responseInfo")
                }

                override fun onFailure(errorInfo: ErrorInfo) {
                    Log.e("$TAG MainActivity", "sendMessageToPeer faid ${errorInfo}")
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
                        "$TAG MainActivity",
                        "testunsubscribePeersOnlineStatus success $responseInfo"
                    )
                }

                override fun onFailure(errorInfo: ErrorInfo) {
                    Log.e(
                        "$TAG MainActivity",
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
        createInstance?.subscribePeersOnlineStatus(
            set,
            object : ResultCallback<Void> {
                override fun onSuccess(responseInfo: Void?) {
                    Log.e("$TAG MainActivity", "queryPeersOnlineStatus success $responseInfo")
                }

                override fun onFailure(errorInfo: ErrorInfo) {
                    Log.e(
                        "$TAG MainActivity",
                        "queryPeersOnlineStatus failure" + errorInfo.errorMsg
                    )
                }

            })
    }

    fun testqueryPeersOnlineStatus() {
        var set: java.util.Set<String> = HashSet<String>() as java.util.Set<String>
        set.add("2")
        set.add("3")
        set.add("4")
        createInstance?.queryPeersOnlineStatus(set,
            object : ResultCallback<Map<String, Boolean>> {
                override fun onSuccess(responseInfo: Map<String, Boolean>?) {
                    Log.e("$TAG MainActivity", "queryPeersOnlineStatus success $responseInfo")
                }

                override fun onFailure(errorInfo: ErrorInfo) {
                    Log.e(
                        "$TAG MainActivity",
                        "queryPeersOnlineStatus failure" + errorInfo.errorMsg
                    )
                }
            })
    }
}