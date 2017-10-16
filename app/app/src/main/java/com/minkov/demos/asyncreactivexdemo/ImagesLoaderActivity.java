package com.minkov.demos.asyncreactivexdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.InputStream;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImagesLoaderActivity extends AppCompatActivity {
    private ListView mListActivity;
    private ArrayAdapter<Bitmap> mAdapter;

    private class Downloader {
        public Observable<Bitmap> load(final String[] urls) {
            return Observable.create(new ObservableOnSubscribe<Bitmap>() {
                @Override
                public void subscribe(@NonNull final ObservableEmitter<Bitmap> e) throws Exception {
                    int index = -1;
                    for (final String url :
                            urls) {
                        index++;
                        final int finalIndex = index;
                        Observable
                                .create(new ObservableOnSubscribe<Bitmap>() {
                                    @Override
                                    public void subscribe(@NonNull ObservableEmitter<Bitmap> e) throws Exception {
                                        OkHttpClient client = new OkHttpClient();
                                        Request req = new Request.Builder()
                                                .url(url)
                                                .build();

                                        Response res = client.newCall(req).execute();

                                        InputStream content = res.body().byteStream();
                                        Bitmap bmp = BitmapFactory.decodeStream(content);
                                        e.onNext(bmp);
                                    }
                                })
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Bitmap>() {
                                    @Override
                                    public void accept(Bitmap s) throws Exception {
                                        e.onNext(s);
                                    }
                                });
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mAdapter = new ArrayAdapter<Bitmap>(this, android.R.layout.simple_list_item_1) {
            @android.support.annotation.NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @android.support.annotation.NonNull ViewGroup parent) {
                ImageView view = (convertView == null || !(convertView instanceof ImageView))
                        ? new ImageView(getContext())
                        : (ImageView) convertView;
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
                view.setLayoutParams(lp);
                view.setImageBitmap(getItem(position));
                return view;
            }
        };

        mListActivity = (ListView)

                findViewById(R.id.lv);
        mListActivity.setAdapter(mAdapter);

        Downloader downloader = new Downloader();
        String[] imgs = new String[]{
                "https://cdn.vox-cdn.com/thumbor/86YEkWdc3LcTshptye6-cdJaIKE=/0x0:1920x1080/1600x900/cdn.vox-cdn.com/uploads/chorus_image/image/55123071/batman_davidfinch.0.jpg",
                "http://cdn.wccftech.com/wp-content/uploads/2016/12/Batman-Arkham.jpg",
                "http://cdn2-www.superherohype.com/assets/uploads/2013/11/batmane3-1.jpg",
        };

        downloader.load(imgs)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap s) throws Exception {
                        mAdapter.add(s);
                    }
                });

    }
}