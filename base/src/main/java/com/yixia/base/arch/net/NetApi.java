package com.yixia.base.arch.net;


import com.yixia.network.ApiBase;

import retrofit2.Retrofit;

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public final class NetApi extends ApiBase {
    private static volatile NetApi instance = null;

    private NetApi() {
        super("http://poetry.apiopen.top/");
    }

    public static NetApi getInstance() {
        if (instance == null) {
            synchronized (NetApi.class) {
                if (instance == null) {
                    instance = new NetApi();
                }
            }
        }
        return instance;
    }

    public Retrofit getRef(){
        return retrofit;
    }
}
