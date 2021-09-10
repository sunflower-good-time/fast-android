package com.yixia.fastandroid.net;

import com.yixia.fastandroid.bean.NewsChannelsBean;


import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public interface NewsApiInterface {
    @GET("sentences")
    Observable<NewsChannelsBean> getNewsChannels();
}
