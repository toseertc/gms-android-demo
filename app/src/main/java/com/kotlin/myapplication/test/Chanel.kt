package com.duobeiyun.generamessagedemo.test

import android.util.Log
import com.duobeiyun.generamessagesdk.ErrorInfo
import com.duobeiyun.generamessagesdk.ResultCallback
import com.duobeiyun.generamessagesdk.channel.GmsChannel
import com.duobeiyun.generamessagesdk.channel.GmsChannelListener
import com.duobeiyun.generamessagesdk.channel.bean.ChannelAttributeOptions
import com.duobeiyun.generamessagesdk.channel.bean.GmsChannelAttribute
import com.duobeiyun.generamessagesdk.channel.bean.GmsChannelMember
import com.duobeiyun.generamessagesdk.client.GmsClient
import com.duobeiyun.generamessagesdk.connect.bean.GmsMessage

/*
 * Author: cqr
 * Version: V100R001C01
 * Create: 2020/6/19 14:00
 * Changes (from 2020/6/19)
 */
class Chanel(var createInstance: GmsClient, var userId: String, var channelId: String = "999") {
    var createChannel: GmsChannel? = null
    fun beginTest() {
        testCreateChanel();
        testJoinChannel();

        testChanelMsg();
        testSetChanelAtt();
        testGetChanelAtt()
        testgetChannelAttributes()
        testDeleteChannelAttributesByKeys();
        testgetChannelAttributes()
        testclearChannelAttributes();
        testgetChannelAttributes()
        testgetChannelMemberCount()

        testgetChannelMembers();
        testLeaveChanel();
    }

    fun testgetChannelMembers() {
        createChannel?.getChannelMembers(object : ResultCallback<List<String>> {
            override fun onSuccess(responseInfo: List<String>?) {
                responseInfo?.forEach() {
                    Log.e(
                        "MainActivity",
                        "testgetChannelMembers $it"
                    )
                }
            }

            override fun onFailure(errorInfo: ErrorInfo) {
                Log.e(
                    "MainActivity",
                    "testgetChannelMembers onFailure $errorInfo"
                )
            }

        })

    }

    fun testgetChannelMemberCount() {
        createInstance?.getChannelMemberCount(
            mutableListOf<String>("11", "222", "222"),
            object : ResultCallback<Map<String, Int>> {
                override fun onFailure(errorInfo: ErrorInfo) {
                    Log.e(
                        "MainActivity",
                        "testgetChannelMemberCount onFailure $errorInfo"
                    )
                }

                override fun onSuccess(responseInfo: Map<String, Int>?) {
                    if (responseInfo != null) {
                        responseInfo.forEach() {
                            Log.e(
                                "MainActivity",
                                "testgetChannelMemberCount ${it.key} ---${it.value} "
                            )
                        }
                    }
                }
            })
    }

    fun testgetChannelAttributes() {
        createInstance?.getChannelAttributes(channelId,
            object : ResultCallback<List<GmsChannelAttribute>> {
                override fun onSuccess(responseInfo: List<GmsChannelAttribute>?) {
                    responseInfo?.forEach({
                        Log.e("MainActivity", "$channelId --- :${it.key} -- ${it.value}")
                    })
                }

                override fun onFailure(errorInfo: ErrorInfo) {
                    Log.e(
                        "MainActivity",
                        "getChannelAttributes onFailure faile $errorInfo"
                    )
                }

            })
    }

    fun testclearChannelAttributes() {
        createInstance?.clearChannelAttributes(
            channelId,
            ChannelAttributeOptions(true),
            object : ResultCallback<Void> {
                override fun onSuccess(responseInfo: Void?) {
                    Log.e(
                        "MainActivity",
                        "clearChannelAttributes success:  " + responseInfo?.toString()
                    )
                }

                override fun onFailure(errorInfo: ErrorInfo) {
                    Log.e(
                        "MainActivity",
                        "clearChannelAttributes onFailure $errorInfo"
                    )
                }
            })
    }

    fun testDeleteChannelAttributesByKeys() {
        createInstance?.deleteChannelAttributesByKeys(
            channelId,
            mutableListOf("color"),
            ChannelAttributeOptions(true),
            object : ResultCallback<Void> {
                override fun onSuccess(responseInfo: Void?) {
                    Log.e(
                        "MainActivity",
                        "testDeleteChannelAttributesByKeys success :" + responseInfo?.toString()
                    )
                }

                override fun onFailure(errorInfo: ErrorInfo) {
                    Log.e(
                        "MainActivity",
                        "testDeleteChannelAttributesByKeys onFailure $errorInfo"
                    )

                }
            })
    }

    fun testCreateChanel() {
        createChannel =
            createInstance?.createChannel(channelId, object : GmsChannelListener {
                override fun onMemberJoined(member: GmsChannelMember) {
                }

                override fun onMemberLeft(member: GmsChannelMember) {
                }

                override fun onMessageReceived(
                    gmsMessage: GmsMessage,
                    member: GmsChannelMember
                ) {
                    Log.e("MainActivity", "onMessageReceived $gmsMessage   $member")
                }

                override fun onAttributesUpdated(attrList: List<GmsChannelAttribute>) {
                    Log.e("MainActivity", "onAttributesUpdated $attrList")
                }
            })
    }

    fun testJoinChannel() {
        createChannel?.join(object : ResultCallback<Void> {
            override fun onSuccess(responseInfo: Void?) {
                Log.e("MainActivity", "join success")
                testChanelMsg()
            }

            override fun onFailure(errorInfo: ErrorInfo) {
                Log.e("MainActivity", "join failure$errorInfo")
            }
        })
    }

    fun testChanelMsg() {
        val createMessage = createInstance?.createMessage(channelId + " msg send ")
        createChannel?.sendMessage(createMessage, object : ResultCallback<Void> {
            override fun onSuccess(responseInfo: Void?) {
                Log.e("MainActivity", "sendMessage success")
            }

            override fun onFailure(errorInfo: ErrorInfo) {
                Log.e("MainActivity", "sendMessage failure :" + errorInfo.errorMsg)

            }
        })
    }

    fun testSetChanelAtt() {
        createInstance?.setChannelAttributes(channelId, mutableListOf(
            GmsChannelAttribute("color", "red"),
            GmsChannelAttribute("background", "yellow"),
            GmsChannelAttribute("title", "")
        ), ChannelAttributeOptions(true), object : ResultCallback<Void> {
            override fun onSuccess(responseInfo: Void?) {
                Log.e("MainActivity", "setChannelAttributes success")
            }

            override fun onFailure(errorInfo: ErrorInfo) {
                Log.e(
                    "MainActivity",
                    "setChannelAttributes failure" + errorInfo.errorMsg
                )
            }
        })
    }

    fun testGetChanelAtt() {
        createInstance?.getChannelAttributesByKeys(
            channelId,
            mutableListOf<String>("background"),
            object : ResultCallback<List<GmsChannelAttribute>> {
                override fun onSuccess(responseInfo: List<GmsChannelAttribute>?) {
                    Log.e("MainActivity", "setChannelAttributes success $responseInfo")
                }

                override fun onFailure(errorInfo: ErrorInfo) {
                    Log.e(
                        "MainActivity",
                        "getChannelAttributes failure" + errorInfo.errorMsg
                    )
                }

            })
    }


    fun testLeaveChanel() {
        createChannel?.leave(object : ResultCallback<Void> {
            override fun onSuccess(responseInfo: Void?) {
                Log.e("MainActivity", "chanel leave success")
            }

            override fun onFailure(errorInfo: ErrorInfo) {
                Log.e("MainActivity", "chanel leave onFailure $errorInfo")
            }
        })
        createChannel = null
    }
}