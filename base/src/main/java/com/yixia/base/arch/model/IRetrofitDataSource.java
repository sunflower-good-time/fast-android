package com.yixia.base.arch.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;


import com.yixia.base.arch.model.impl.IRetrofit;
import com.yixia.base.arch.net.NetApi;
import com.yixia.network.ApiBase;
import com.yixia.network.errorhandler.ExceptionHandle;
import com.yixia.network.observer.BaseObserver;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Kang Wei
 * @date 2019/10/29
 */
public abstract class IRetrofitDataSource<P, T> extends BaseDataSource<P, T> implements IRetrofit<P, T> {
    // 设置网络检查的注解
    @Override
    protected void fetchData(@Nullable P request, @NonNull Consumer<T> callback) {
        NetApi.getInstance().ApiSubscribe(getCall(request), new BaseObserver<T>(){
            @Override
            public void onNext(@NonNull T netT) {
                callback.accept(netT);
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                callback.accept(null);
            }
        });
    }
}
