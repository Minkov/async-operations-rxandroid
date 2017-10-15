package com.minkov.demos.asyncreactivexdemo.http;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by minkov on 10/15/17.
 */

public class HttpRequester {
    private final OkHttpClient mOkHttpClient;

    public HttpRequester() {
        mOkHttpClient = new OkHttpClient();
    }

    public String getFrom(String url) throws IOException {
        Request req = new Request.Builder()
                .url(url)
                .build();

        Response res = mOkHttpClient.newCall(req)
                .execute();
        String body = res.body().string();
        return body;
    }
}
