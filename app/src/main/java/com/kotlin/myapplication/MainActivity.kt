package com.kotlin.myapplication

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.duobeiyun.generamessagedemo.test.TestManager
import com.duobeiyun.generamessagesdk.ErrorInfo
import com.duobeiyun.generamessagesdk.ResultCallback
import com.duobeiyun.generamessagesdk.bean.LoginParams
import com.duobeiyun.generamessagesdk.channel.GmsChannel
import com.duobeiyun.generamessagesdk.channel.GmsChannelListener
import com.duobeiyun.generamessagesdk.channel.bean.ChannelAttributeOptions
import com.duobeiyun.generamessagesdk.channel.bean.GmsChannelAttribute
import com.duobeiyun.generamessagesdk.channel.bean.GmsChannelMember
import com.duobeiyun.generamessagesdk.client.GmsClient
import com.duobeiyun.generamessagesdk.client.GmsClientListener
import com.duobeiyun.generamessagesdk.connect.bean.GmsMessage
import com.duobeiyun.generamessagesdk.user.GmsAttribute
import com.duobeiyun.generamessagesdk.utils.TokenUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.util.Set

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var createInstance: GmsClient? = null
    var createChannel999: GmsChannel? = null
    var createChannel111: GmsChannel? = null
    var userId: String? = null
    val TEST_APPID = "1824a0bff47e4f47bdce956c5e9025ad";
    val TEST_APPKEY = "24f5595baa8645fd947051e75ffcdcc4";
    var testManger: TestManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        initListeners()
    }

    var token: String? = null
    var timestamp: Long? = null
    private fun init() {
        //Android 自己测试呼叫邀请需要多台设备，demo：uid: gms_android 作为邀请方，uid：1111 作为被邀请方
        //用户可以随意设置
        userId = "gms_android"// + Random.nextInt(1000).toString()
        /**此为demo 生成token，用户对接，需要从自己的服务器进行请求，不要将appkey放在客户端 不安全*/
        timestamp = 0
        token = TokenUtils.createToke(TEST_APPID, userId, timestamp!!, TEST_APPKEY);
        createClientInstance()
        testManger = TestManager(createInstance!!, userId!!, this)

        val testInviterIdDefault = "gms_android"
        testManger?.TestAsInviter = testInviterIdDefault.equals(userId)
    }

    private fun createClientInstance() {


        createInstance = GmsClient.createInstance(
            applicationContext,
            TEST_APPID,
            object : GmsClientListener {
                override fun onConnectionStateChanged(code: Int, reason: Int) {
                    // 连接状态发生改变
                    Log.e(
                        "MainActivity",
                        "GmsClientListener.onConnectionStateChanged $code   $reason"
                    )
                }

                override fun onMessageReceived(message: GmsMessage?, userId: String) {
                    Log.e("MainActivity", "GmsClientListener.onMessageReceived $message   $userId")

                }

                override fun onPeersOnlineStatusChanged(map: Map<String, Int>) {
//                    PeerOnlineState int 对应值
                    Log.e("MainActivity", "GmsClientListener.onPeersOnlineStatusChanged $map")
                }

                override fun onTokenExpired() {
                    /**此为demo 生成token，用户对接，需要从自己的服务器进行请求，不要将appkey放在客户端 不安全*/
                    timestamp = System.currentTimeMillis();
                    token = TokenUtils.createToke(TEST_APPID, userId, timestamp!!, TEST_APPKEY);
                    createInstance?.renewToken(LoginParams(token!!, userId!!, timestamp!!),
                        object : ResultCallback<Void> {
                            override fun onSuccess(responseInfo: Void?) {
                                Log.e(
                                    "MainActivity",
                                    "onTokenExpired success"
                                )
                            }

                            override fun onFailure(errorInfo: ErrorInfo) {
                                Log.e(
                                    "MainActivity",
                                    "onTokenExpired failed"
                                )
                            }

                        })
                }
            })
    }

    private fun initListeners() {
        test_interface.setOnClickListener(this)
        //
        login.setOnClickListener(this)
        join_channel_111.setOnClickListener(this)
        leave_channel_111.setOnClickListener(this)
        join_channel_999.setOnClickListener(this)
        leave_channel_999.setOnClickListener(this)
//
        btn_send_channel_msg.setOnClickListener(this)
//
        btn_set_attr.setOnClickListener(this);
        btn_set_attr_t.setOnClickListener(this);
        btn_get_attr.setOnClickListener(this);
        btn_get_attr_t.setOnClickListener(this);
        btn_set_user_attr.setOnClickListener(this);
        btn_get_user_attr.setOnClickListener(this);
        btn_query_user_status.setOnClickListener(this)
        logout.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.test_interface -> testManger?.beginTest()
            R.id.login -> login()
            R.id.join_channel_999 -> {
                createChannel999 = joinChanel("9999")
            }
            R.id.join_channel_111 -> {
                createChannel111 = joinChanel("1111")
            }
            R.id.leave_channel_999 ->
                leaveChanel(createChannel999!!)
            R.id.leave_channel_111 ->
                leaveChanel(createChannel111!!)
            R.id.btn_send_channel_msg ->
                sendChannelMsg()
            R.id.btn_set_attr ->
                setChannelAttributes("999")
            R.id.btn_set_attr_t ->
                setChannelAttributes("111")
            R.id.btn_get_attr ->
                getChannelAttributesByKeys("999")
            R.id.btn_get_attr_t ->
                getChannelAttributesByKeys("111")
            R.id.btn_set_user_attr ->
                setLocalUserAttributes()
            R.id.btn_get_user_attr ->
                getUserAttributes()
            R.id.btn_query_user_status ->
                queryPeersOnlineStatus()
            R.id.logout ->
                logout()
        }
    }

    private fun logout() {
        createInstance?.logout(object : ResultCallback<Void> {
            override fun onSuccess(responseInfo: Void?) {
                Log.e("MainActivity", "logout success")
            }

            override fun onFailure(errorInfo: ErrorInfo) {
                Log.e("MainActivity", "logout faield $errorInfo")
            }
        })
        createInstance?.release()
    }

    override fun onDestroy() {
        super.onDestroy()
        createInstance?.release()
    }

    private fun queryPeersOnlineStatus() {
        createInstance?.queryPeersOnlineStatus(
            mutableSetOf<String>("11", "2", "3") as Set<String>,
            object : ResultCallback<Map<String, Boolean>> {
                override fun onSuccess(responseInfo: Map<String, Boolean>?) {
                    Log.e("MainActivity", "queryPeersOnlineStatus success $responseInfo")
                }

                override fun onFailure(errorInfo: ErrorInfo) {
                    Log.e(
                        "MainActivity",
                        "queryPeersOnlineStatus failure" + errorInfo.errorMsg
                    )
                }
            })
    }

    private fun login() {
        createInstance?.login(
            LoginParams(token!!, userId!!, timestamp!!),
            object : ResultCallback<Void> {
                override fun onSuccess(responseInfo: Void?) {
                    Log.e("MainActivity", "login success")
                }

                override fun onFailure(errorInfo: ErrorInfo) {
                    Log.e("MainActivity", "login failure：" + errorInfo.errorMsg)
                }
            })
    }

    private fun joinChanel(channelId: String): GmsChannel? {
        var chanel: GmsChannel? =
            createInstance?.createChannel(channelId, object : GmsChannelListener {
                override fun onMemberJoined(member: GmsChannelMember) {
                }

                override fun onMemberLeft(member: GmsChannelMember) {
                }

                override fun onMessageReceived(gmsMessage: GmsMessage, member: GmsChannelMember) {
                    Log.e("MainActivity", "onMessageReceived $gmsMessage   $member")
                }

                override fun onAttributesUpdated(attrList: List<GmsChannelAttribute>) {
                    Log.e("MainActivity", "onAttributesUpdated $attrList")
                }

                override fun onMemberCountUpdated(count: Int) {
                    Log.e("MainActivity", "onMemberCountUpdated channel:$channelId ,count: $count")
                }
            })
        chanel?.join(object : ResultCallback<Void> {
            override fun onSuccess(responseInfo: Void?) {
                Log.e("MainActivity", "join success")
            }

            override fun onFailure(errorInfo: ErrorInfo) {
                Log.e("MainActivity", "join failure$errorInfo")
            }
        })
        return chanel;
    }

    private fun leaveChanel(gmsChanel: GmsChannel) {
        gmsChanel?.leave(object : ResultCallback<Void> {
            override fun onSuccess(responseInfo: Void?) {
                Log.e("MainActivity", "channel leave success")
            }

            override fun onFailure(errorInfo: ErrorInfo) {
                Log.e("MainActivity", "channel leave onFailure $errorInfo")
            }
        })
        gmsChanel.release()
    }

    private fun sendChannelMsg() {
        val msg = et_channel_msg.text.trim().toString()
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        val createMessage = createInstance?.createMessage(msg)
            ?: return
        createChannel999?.sendMessage(createMessage, object : ResultCallback<Void> {
            override fun onSuccess(responseInfo: Void?) {
                Log.e("MainActivity", "sendMessage success")
            }

            override fun onFailure(errorInfo: ErrorInfo) {
                Log.e("MainActivity", "sendMessage failure:" + errorInfo.errorMsg)

            }
        })

        createInstance?.createMessage();
        createInstance?.createMessage(arrayOf<Byte>(0))
    }

    private fun setChannelAttributes(channelId: String) {
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

    private fun getChannelAttributesByKeys(channelId: String) {
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

    private fun setLocalUserAttributes() {
        createInstance?.setLocalUserAttributes(mutableListOf(
            GmsAttribute("name", "yy"),
            GmsAttribute("age", "27")
        ), object : ResultCallback<Void> {
            override fun onSuccess(responseInfo: Void?) {
                Log.e("MainActivity", "setLocalUserAttributes success $responseInfo")
            }

            override fun onFailure(errorInfo: ErrorInfo) {
                Log.e(
                    "MainActivity",
                    "setLocalUserAttributes failure" + errorInfo.errorMsg
                )
            }

        })
    }

    private fun getUserAttributes() {
        createInstance?.getUserAttributes(userId!!,
            object : ResultCallback<List<GmsAttribute>> {
                override fun onSuccess(responseInfo: List<GmsAttribute>?) {
                    Log.e("MainActivity", "getUserAttributes success $responseInfo")
                }

                override fun onFailure(errorInfo: ErrorInfo) {
                    Log.e(
                        "MainActivity",
                        "getUserAttributes failure" + errorInfo.errorMsg
                    )
                }
            })
    }
}
