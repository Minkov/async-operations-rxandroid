package com.minkov.demos.asyncreactivexdemo.repos.base;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by minkov on 10/18/17.
 */

public interface BaseRepository<T> {
    void getWithAsyncTask(OnDataReceivedListener<T> listener);

    Observable<List<T>> getWithObservable();

    interface OnDataReceivedListener<T> {
        void onSuccess(List<T> list);
        void onError(Throwable ex);
    }
}
