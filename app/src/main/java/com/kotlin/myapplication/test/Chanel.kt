package com.rz.gmsdemo.test

import android.util.Log
import com.rz.gms.ErrorInfo
import com.rz.gms.ResultCallback
import com.rz.gms.channel.GMSChannel
import com.rz.gms.channel.GMSChannelListener
import com.rz.gms.channel.bean.ChannelAttributeOptions
import com.rz.gms.channel.bean.GMSChannelAttribute
import com.rz.gms.channel.bean.GMSChannelMember
import com.rz.gms.client.GMSClient
import com.rz.gms.connect.bean.GMSMessage

/*
 * Author: cqr
 * Version: V100R001C01
 * Create: 2020/6/19 14:00
 * Changes (from 2020/6/19)
 */
class Chanel(var createInstance: GMSClient, var userId: String, var channelId: String = "999") {
    var createChannel: GMSChannel? = null
    var TAG = "***Chanel***"
    fun beginTest() {
        testCreateChanel();
        testJoinChannel();

        testChanelMsg();
        testSetChanelAtt();
        testGetChanelAtt()
        testAddOrUpdateChannelAttributes()
        testgetChannelAttributes()
        testDeleteChannelAttributesByKeys();
        testgetChannelAttributes()
        testclearChannelAttributes();
        testgetChannelAttributes()
        testgetChannelMemberCount()

        testgetChannelMembers();
        testLeaveChanel();
    }

    private fun testAddOrUpdateChannelAttributes() {
        createInstance?.addOrUpdateChannelAttributes(
            channelId,
            GMSChannelAttribute("color", "colorTestChange"),
            ChannelAttributeOptions(true),
            object : ResultCallback<Void> {
                override fun onSuccess(responseInfo: Void?) {
                    Log.e(
                        "MainActivity",
                        "$TAG testAddOrUpdateChannelAttributes success"
                    )
                }

                override fun onFailure(errorInfo: ErrorInfo) {
                    Log.e(
                        "MainActivity",
                        "$TAG testAddOrUpdateChannelAttributes failed"
                    )
                }
            })
    }

    fun testgetChannelMembers() {
        createChannel?.getChannelMembers(object : ResultCallback<List<String>> {
            override fun onSuccess(responseInfo: List<String>?) {
                responseInfo?.forEach() {
                    Log.e(
                        "MainActivity",
                        "$TAG testgetChannelMembers $it"
                    )
                }
            }

            override fun onFailure(errorInfo: ErrorInfo) {
                Log.e(
                    "MainActivity",
                    "$TAG testgetChannelMembers onFailure $errorInfo"
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
                        "$TAG testgetChannelMemberCount onFailure $errorInfo"
                    )
                }

                override fun onSuccess(responseInfo: Map<String, Int>?) {
                    if (responseInfo != null) {
                        responseInfo.forEach() {
                            Log.e(
                                "MainActivity",
                                "$TAG testgetChannelMemberCount ${it.key} ---${it.value} "
                            )
                        }
                    }
                }
            })
    }

    fun testgetChannelAttributes() {
        createInstance?.getChannelAttributes(
            channelId,
            object : ResultCallback<List<GMSChannelAttribute>> {
                override fun onSuccess(responseInfo: List<GMSChannelAttribute>?) {
                    responseInfo?.forEach({
                        Log.e(
                            "MainActivity",
                            "$TAG getChannelAttributes $channelId --- :${it.key} -- ${it.value}"
                        )
                    })
                }

                override fun onFailure(errorInfo: ErrorInfo) {
                    Log.e(
                        "MainActivity",
                        "$TAG getChannelAttributes onFailure faile $errorInfo"
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
                        "$TAG clearChannelAttributes success:  " + responseInfo?.toString()
                    )
                }

                override fun onFailure(errorInfo: ErrorInfo) {
                    Log.e(
                        "MainActivity",
                        "$TAG clearChannelAttributes onFailure $errorInfo"
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
                        "$TAG testDeleteChannelAttributesByKeys success :" + responseInfo?.toString()
                    )
                }

                override fun onFailure(errorInfo: ErrorInfo) {
                    Log.e(
                        "MainActivity",
                        "$TAG testDeleteChannelAttributesByKeys onFailure $errorInfo"
                    )

                }
            })
    }

    fun testCreateChanel() {
        createChannel =
            createInstance?.createChannel(channelId, object : GMSChannelListener {
                override fun onMemberJoined(member: GMSChannelMember) {
                }

                override fun onMemberLeft(member: GMSChannelMember) {
                }

                override fun onMessageReceived(
                    gmsMessage: GMSMessage,
                    member: GMSChannelMember
                ) {
                    Log.e("MainActivity", "$TAG onMessageReceived $gmsMessage   $member")
                }

                override fun onAttributesUpdated(attrList: List<GMSChannelAttribute>) {
                    Log.e("MainActivity", "$TAG onAttributesUpdated $attrList")
                }

                override fun onMemberCountUpdated(count: Int) {
                    Log.e(
                        "MainActivity",
                        "$TAG onMemberCountUpdated channel:$channelId ,count: $count"
                    )
                }
            })
    }

    fun testJoinChannel() {
        createChannel?.join(object : ResultCallback<Void> {
            override fun onSuccess(responseInfo: Void?) {
                Log.e("MainActivity", "$TAG join success")
                testChanelMsg()
            }

            override fun onFailure(errorInfo: ErrorInfo) {
                Log.e("MainActivity", "$TAG join failure$errorInfo")
            }
        })
    }

    fun testChanelMsg() {
        val createMessage = createInstance?.createMessage(channelId + " msg send ")
        createChannel?.sendMessage(createMessage, object : ResultCallback<Void> {
            override fun onSuccess(responseInfo: Void?) {
                Log.e("MainActivity", "$TAG sendMessage success")
            }

            override fun onFailure(errorInfo: ErrorInfo) {
                Log.e("MainActivity", "$TAG sendMessage failure :" + errorInfo.errorMsg)

            }
        })
    }

    fun testSetChanelAtt() {
        createInstance?.setChannelAttributes(channelId, mutableListOf(
            GMSChannelAttribute("color", "red"),
            GMSChannelAttribute("background", "yellow"),
            GMSChannelAttribute("title", "")
        ), ChannelAttributeOptions(true), object : ResultCallback<Void> {
            override fun onSuccess(responseInfo: Void?) {
                Log.e("MainActivity", "$TAG setChannelAttributes success")
            }

            override fun onFailure(errorInfo: ErrorInfo) {
                Log.e(
                    "MainActivity",
                    "$TAG setChannelAttributes failure" + errorInfo.errorMsg
                )
            }
        })
    }

    fun testGetChanelAtt() {
        createInstance?.getChannelAttributesByKeys(
            channelId,
            mutableListOf<String>("background"),
            object : ResultCallback<List<GMSChannelAttribute>> {
                override fun onSuccess(responseInfo: List<GMSChannelAttribute>?) {
                    Log.e("MainActivity", "$TAG getChannelAttributesByKeys success $responseInfo")
                }

                override fun onFailure(errorInfo: ErrorInfo) {
                    Log.e(
                        "MainActivity",
                        "$TAG getChannelAttributes failure" + errorInfo.errorMsg
                    )
                }

            })
    }


    fun testLeaveChanel() {
        createChannel?.leave(object : ResultCallback<Void> {
            override fun onSuccess(responseInfo: Void?) {
                Log.e("MainActivity", "$TAG chanel leave success")
            }

            override fun onFailure(errorInfo: ErrorInfo) {
                Log.e("MainActivity", "$TAG chanel leave onFailure $errorInfo")
            }
        })
        createChannel = null
    }
}