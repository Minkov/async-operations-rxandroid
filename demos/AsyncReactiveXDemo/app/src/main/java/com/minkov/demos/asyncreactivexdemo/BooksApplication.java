package com.minkov.demos.asyncreactivexdemo;

import android.app.Application;

import com.minkov.demos.asyncreactivexdemo.services.BooksService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by minkov on 10/18/17.
 */

public class BooksApplication extends Application {
    private Retrofit mRetrofit;

    private Retrofit getRetrofit() {
        if(mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return mRetrofit;
    }

    public BooksService getBooksService() {
        return getRetrofit()
                .create(BooksService.class);
    }
}
