package com.yixia.fastandroid.page.home.model.source;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yixia.base.arch.model.IRetrofitDataSource;
import com.yixia.base.arch.net.NetApi;
import com.yixia.fastandroid.bean.NewsChannelsBean;
import com.yixia.fastandroid.net.NewsApiInterface;

import io.reactivex.Observable;

/**
 * @author : zhoujianfeng
 * CreatData: 2021/9/13
 * desc:
 */
public class NewTestSource extends IRetrofitDataSource<String, NewsChannelsBean> {
    @NonNull
    @Override
    public Observable<NewsChannelsBean> getCall(@Nullable String request) {
        return NetApi.getInstance().getRef().create(NewsApiInterface.class).getNewsChannels();
    }
}
