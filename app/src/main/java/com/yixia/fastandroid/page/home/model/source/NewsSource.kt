package com.yixia.fastandroid.page.home.model.source

import com.yixia.base.arch.model.IRetrofitDataSource
import com.yixia.base.arch.net.NetApi
import com.yixia.fastandroid.bean.NewsChannelsBean
import com.yixia.fastandroid.net.NewsApiInterface
import io.reactivex.Observable

/**
 * @author : zhoujianfeng
 * CreatData: 2021/9/13
 * desc:
 */

class NewsSource : IRetrofitDataSource<String, NewsChannelsBean>() {
    override fun getCall(request: String?): Observable<NewsChannelsBean> {
        return NetApi.getInstance().ref.create(NewsApiInterface::class.java).newsChannels
    }
}