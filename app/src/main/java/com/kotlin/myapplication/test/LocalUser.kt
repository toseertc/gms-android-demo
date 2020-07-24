package com.duobeiyun.generamessagedemo.test

import android.util.Log
import com.duobeiyun.generamessagesdk.ErrorInfo
import com.duobeiyun.generamessagesdk.ResultCallback
import com.duobeiyun.generamessagesdk.client.GmsClient
import com.duobeiyun.generamessagesdk.user.GmsAttribute

/*
 * Author: cqr
 * Version: V100R001C01
 * Create: 2020/6/19 14:15
 * Changes (from 2020/6/19)
 */

class LocalUser(var createInstance: GmsClient, var userId: String) {
    val TAG = LocalUser::class.java.name
    fun beginTest() {
        testSetLocalAtt()
        testGetAtt()
        testaddOrUpdateLocalUserAttributes()
        testdeleteLocalUserAttributesByKeys()
        testgetUserAttributes()
        testclearLocalUserAttributes();
        testgetUserAttributes()
        testgetUserAttributesByKeys()
    }

    fun testgetUserAttributesByKeys() {
        createInstance?.getUserAttributesByKeys(
            userId,
            mutableListOf("country"),
            object : ResultCallback<List<GmsAttribute>> {
                override fun onSuccess(responseInfo: List<GmsAttribute>?) {
                    Log.e(
                        TAG,
                        "testgetUserAttributesByKeys success $responseInfo"
                    )
                }

                override fun onFailure(errorInfo: ErrorInfo) {
                    Log.e(
                        TAG,
                        "testgetUserAttributesByKeys failure" + errorInfo.errorMsg
                    )
                }

            })
    }

    fun testgetUserAttributes() {
        createInstance?.getUserAttributes(userId, object : ResultCallback<List<GmsAttribute>> {
            override fun onSuccess(responseInfo: List<GmsAttribute>?) {
                Log.e(
                    TAG,
                    "testgetUserAttributes success $responseInfo"
                )
            }

            override fun onFailure(errorInfo: ErrorInfo) {
                Log.e(
                    TAG,
                    "testgetUserAttributes failure" + errorInfo.errorMsg
                )
            }

        })
    }

    fun testclearLocalUserAttributes() {
        createInstance?.clearLocalUserAttributes(object : ResultCallback<Void> {
            override fun onSuccess(responseInfo: Void?) {
                Log.e(
                    TAG,
                    "testclearLocalUserAttributes success $responseInfo"
                )
            }

            override fun onFailure(errorInfo: ErrorInfo) {
                Log.e(
                    TAG,
                    "testclearLocalUserAttributes failure" + errorInfo.errorMsg
                )
            }

        })
    }

    fun testdeleteLocalUserAttributesByKeys() {
        createInstance?.deleteLocalUserAttributesByKeys(
            mutableListOf("city"),
            object : ResultCallback<Void> {
                override fun onSuccess(responseInfo: Void?) {
                    Log.e(
                        TAG,
                        "testdeleteLocalUserAttributesByKeys success $responseInfo"
                    )
                }

                override fun onFailure(errorInfo: ErrorInfo) {
                    Log.e(
                        TAG,
                        "testdeleteLocalUserAttributesByKeys failure" + errorInfo.errorMsg
                    )
                }

            })
    }

    fun testaddOrUpdateLocalUserAttributes() {
        var list = mutableListOf(
            GmsAttribute("name", "duobeiGms"),
            GmsAttribute("age", "1000000000000")
        )
        createInstance?.addOrUpdateLocalUserAttributes(list,
            object : ResultCallback<Void> {
                override fun onSuccess(responseInfo: Void?) {
                    Log.e(
                        TAG,
                        "testaddOrUpdateLocalUserAttributes success $responseInfo"
                    )
                    list.clear()
                    testgetUserAttributes()
                }

                override fun onFailure(errorInfo: ErrorInfo) {
                    list.clear()
                    Log.e(
                        TAG,
                        "testaddOrUpdateLocalUserAttributes failure" + errorInfo.errorMsg
                    )
                }

            })
    }

    fun testSetLocalAtt() {
        var list = mutableListOf(
            GmsAttribute("name", "yy"),
            GmsAttribute("age", "27"),
            GmsAttribute("city", "beijing"),
            GmsAttribute("country", "china")
        );
        createInstance?.setLocalUserAttributes(
            list, object : ResultCallback<Void> {
                override fun onSuccess(responseInfo: Void?) {
                    Log.e(TAG, "setLocalUserAttributes success $responseInfo")
                    list.clear()
                    testgetUserAttributes()
                }

                override fun onFailure(errorInfo: ErrorInfo) {
                    list.clear()
                    Log.e(
                        TAG,
                        "setLocalUserAttributes failure" + errorInfo.errorMsg
                    )
                }

            })
    }

    fun testGetAtt() {
        createInstance?.getUserAttributes(userId!!,
            object : ResultCallback<List<GmsAttribute>> {
                override fun onSuccess(responseInfo: List<GmsAttribute>?) {
                    Log.e(TAG, "getUserAttributes success $responseInfo")
                }

                override fun onFailure(errorInfo: ErrorInfo) {
                    Log.e(
                        TAG,
                        "getUserAttributes failure" + errorInfo.errorMsg
                    )
                }
            })
    }
}