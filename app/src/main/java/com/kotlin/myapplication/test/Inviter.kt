package com.duobeiyun.generamessagedemo.test

import android.content.Context
import android.util.Log
import com.duobeiyun.generamessagesdk.ErrorInfo
import com.duobeiyun.generamessagesdk.ResultCallback
import com.duobeiyun.generamessagesdk.client.GmsClient
import com.duobeiyun.generamessagesdk.invite.GmsInvitationEventListener
import com.duobeiyun.generamessagesdk.invite.Invitation
import com.duobeiyun.generamessagesdk.invite.InvitationFailureReason
import com.duobeiyun.generamessagesdk.invite.InvitationManager

/*
 * Author: cqr
 * Version: V100R001C01
 * Create: 2020/6/22 17:16
 * Changes (from 2020/6/22 )
 *需要俩个客户端测试，需要等接收方登录后在运行实例
 */

class Inviter(var createInstance: GmsClient, var userId: String, var context: Context) {
    private var manager: InvitationManager? = null
    private var invitation: Invitation? = null
    private var Tag = "Invite 邀请方"
    val testInitee = "1111"
    fun beginTest() {
        //发送方
        manager = createInstance.getRtmCallManager()
        setTestListener();
        testcreate();
        if (invitation == null) {
            return
        }
        //send testCancel
        var invitationCancel = manager?.create(testInitee, "from android invitationCancel")
        testSend(invitationCancel!!)
        //testRefuse
        var invitationAccept = manager?.create(testInitee, "from android invitationAccept")
        testSend(invitationAccept!!)
        // testRefuse
        var invitationRefuse = manager?.create(testInitee, "from android invitationRefuse")
        testSend(invitationRefuse!!)

        testTimerCountDown()
        testOutTimeWithOtherAction()
    }

    fun testTimerCountDown() {
        var invitation = manager?.create("77777", "from android")
        if (invitation != null) {
            testSend(invitation)
        }
    }

    fun testOutTimeWithOtherAction() {
        Thread(Runnable {
            var timeOutActionInvite = manager?.create("2222", "from android")
            testSend(timeOutActionInvite!!)
            Thread.sleep(60_000)
            testcancel(timeOutActionInvite!!)
            testAccept(timeOutActionInvite)
            testRefuse(timeOutActionInvite)
        }).start()
    }

    fun testRefuse(invitation2: Invitation = this!!.invitation!!) {
        manager?.refuse(invitation2!!, object : ResultCallback<Invitation> {
            override fun onSuccess(responseInfo: Invitation?) {
                log(responseInfo!!, "refuse");
            }

            override fun onFailure(errorInfo: ErrorInfo) {
                Log.e(
                    "MainActivity",
                    Tag + " refuse onFailure  ${errorInfo} - $invitation2"
                )
            }

        })
    }

    fun testAccept(invitation2: Invitation = this!!.invitation!!) {
        manager?.accept(invitation2!!, object : ResultCallback<Invitation> {
            override fun onSuccess(responseInfo: Invitation?) {
                log(responseInfo!!, "accept");
            }

            override fun onFailure(errorInfo: ErrorInfo) {
                Log.e(
                    "MainActivity",
                    Tag + " accept onFailure  ${errorInfo} --- $invitation2"
                )
            }

        })
    }

    fun testcancel(invitation: Invitation) {
        manager?.cancel(invitation!!, object : ResultCallback<Invitation> {
            override fun onSuccess(responseInfo: Invitation?) {
                log(responseInfo!!, "cancel");
            }

            override fun onFailure(errorInfo: ErrorInfo) {
                Log.e(
                    "${Tag} MainActivity",
                    Tag + " cancel onFailure ${errorInfo}-- $invitation"
                )
            }

        })
    }

    fun testSend(invitation2: Invitation = this!!.invitation!!) {
        manager?.send(invitation2!!, object : ResultCallback<Invitation> {
            override fun onSuccess(responseInfo: Invitation?) {
                log(responseInfo!!, "testSend success:");
            }

            override fun onFailure(errorInfo: ErrorInfo) {
                Log.e(
                    "$Tag MainActivity",
                    Tag + " testSend onFailure ${errorInfo} --$invitation"
                )
            }

        })
    }

    fun testcreate() {
        invitation = manager?.create(userId, "from android")!!
        invitation?.setRequestInfo("---extrainfo--request")
    }

    private fun log(invitation: Invitation, funString: String) {
        Log.e(
            "MainActivity", Tag +
                    " $funString : ${invitation}"
        )
    }

    fun setTestListener() {
        manager?.setEventListener(object : GmsInvitationEventListener {
            override fun onFailure(invitation: Invitation, reason: InvitationFailureReason) {
                log(invitation, "onFailure")
                Log.e(
                    "MainActivity", "setEventListener reason:${reason.reason}"
                )
            }

            override fun onArrived(invitation: Invitation) {
                log(invitation, "onArrived")
            }

            override fun onCanceled(invitation: Invitation) {
                log(invitation, "onCanceled ")
            }

            override fun onAccepted(invitation: Invitation) {

                log(invitation, "onAccepted ")
            }

            override fun onRefused(invitation: Invitation) {
                log(invitation, "onRefused ")
            }

        })
    }
}