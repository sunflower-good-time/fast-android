package com.yixia.base.arch.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.lifecycle.LiveData;

import com.yixia.base.arch.model.impl.IRetrofit;
import com.yixia.base.arch.model.impl.IRoom;
import com.yixia.base.arch.net.NetApi;
import com.yixia.network.ApiBase;
import com.yixia.network.errorhandler.ExceptionHandle;
import com.yixia.network.observer.BaseObserver;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Kang Wei
 * @date 2019/11/2
 */
public abstract class IRetrofitAndRoomDataSource<P, NetT, DbT, Dao> extends IDbAndNetDataSource<P,
        NetT, DbT> implements IRetrofit<P, NetT>, IRoom<P, DbT, Dao> {
    public IRetrofitAndRoomDataSource(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void fromNet(@Nullable P request, @NonNull Consumer<NetT> callback) {
        NetApi.getInstance().ApiSubscribe(getCall(request), new BaseObserver<NetT>(){
            @Override
            public void onNext(@NonNull NetT netT) {
                callback.accept(netT);
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                callback.accept(null);
            }
        });
    }


    @Override
    protected LiveData<DbT> fromDb(@Nullable P request) {
        return query(request);
    }
}
