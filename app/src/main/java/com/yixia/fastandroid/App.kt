package com.yixia.fastandroid

import android.app.Application
import com.yixia.fastandroid.net.NetworkRequestInfo
import com.yixia.network.ApiBase

/**
 * @author : zhoujianfeng
 * CreatData: 2021/9/10
 * desc:
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ApiBase.setNetworkRequestInfo(NetworkRequestInfo())
    }
}