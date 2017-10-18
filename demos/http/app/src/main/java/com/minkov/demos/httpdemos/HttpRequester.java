package com.minkov.demos.httpdemos;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by minkov on 10/16/17.
 */

public class HttpRequester {
    private final OkHttpClient mOkHttpClient;
    private MediaType mJsonMediaType;

    public HttpRequester() {

        mOkHttpClient = new OkHttpClient();
        mJsonMediaType = MediaType.parse("application/json");
    }

    public Observable<String> get(final String url) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                Request req = new Request.Builder()
                        .url(url)
                        .build();
                Response res = mOkHttpClient.newCall(req)
                        .execute();

                e.onNext(res.body().string());
                e.onComplete();
            }
        });
    }

    public Observable<String> post(final String url, final String bodyString) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                RequestBody body = RequestBody.create(mJsonMediaType, bodyString);
                Request req = new Request.Builder()
                        .post(body)
                        .url(url)
                        .build();

                Response res = mOkHttpClient.newCall(req)
                        .execute();

                e.onNext(res.body().string());
                e.onComplete();
            }
        });
    }

    private void setupRequestBuilder(Request.Builder builder) {

    }
}
