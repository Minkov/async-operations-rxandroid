package com.minkov.demos.asyncreactivexdemo.utils;

import com.google.gson.Gson;

/**
 * Created by minkov on 10/15/17.
 */

public class JsonParser<T> {
    private final Class<T> mKlass;
    private final Gson mGson;

    public JsonParser(Class<T> aClass) {
        mKlass = aClass;
        mGson = new Gson();
    }

    public T parse(String json) {
        return mGson.fromJson(json, mKlass);
    }
}
