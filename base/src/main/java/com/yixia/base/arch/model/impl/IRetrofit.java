package com.yixia.base.arch.model.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Observable;
import retrofit2.Call;

/**
 * @author Kang Wei
 * @date 2019/11/6
 */
public interface IRetrofit<P, T> {
    @NonNull
    Observable<T> getCall(@Nullable P request);
}
