package com.kotlin.myapplication.test

import android.util.Log
import cn.tosee.gms.ErrorInfo
import cn.tosee.gms.ResultCallback
import cn.tosee.gms.client.GMSClient
import cn.tosee.gms.user.GMSAttribute

/*
 * Author: cqr
 * Version: V100R001C01
 * Create: 2020/6/19 14:15
 * Changes (from 2020/6/19)
 */

class LocalUser(var createInstance: GMSClient, var userId: String) {
    val TAG = LocalUser::class.java.name
    fun beginTest() {
        testSetLocalAtt()
        testgetUserAttributes()
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
            object : ResultCallback<List<GMSAttribute>> {
                override fun onSuccess(responseInfo: List<GMSAttribute>?) {
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
        createInstance?.getUserAttributes(userId, object : ResultCallback<List<GMSAttribute>> {
            override fun onSuccess(responseInfo: List<GMSAttribute>?) {
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
            GMSAttribute("name", "duobeiGMS"),
            GMSAttribute("age", "1000000000000")
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
            GMSAttribute("name", "yy"),
            GMSAttribute("age", "27"),
            GMSAttribute("city", "beijing"),
            GMSAttribute("country", "china")
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
}