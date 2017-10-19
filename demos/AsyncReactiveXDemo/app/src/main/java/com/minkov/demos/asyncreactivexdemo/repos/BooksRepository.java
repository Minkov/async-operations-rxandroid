package com.minkov.demos.asyncreactivexdemo.repos;

import android.os.AsyncTask;

import com.minkov.demos.asyncreactivexdemo.models.Book;
import com.minkov.demos.asyncreactivexdemo.repos.base.BaseRepository;
import com.minkov.demos.asyncreactivexdemo.services.BooksService;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * Created by minkov on 10/18/17.
 */

public class BooksRepository implements BaseRepository<Book> {

    private final BooksService mService;

    public BooksRepository(BooksService service) {
        mService = service;
    }

    @Override
    public void getWithAsyncTask(final OnDataReceivedListener<Book> listener) {
        AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {
            @Override
            public String doInBackground(String[] strings) {
                try {
                    List<Book> list = mService.listBooks()
                            .execute()
                            .body();
                    listener.onSuccess(list);
                } catch (Exception ex) {
                    listener.onError(ex);
                }
                return null;
            }
        };
        task.execute("");
    }

    @Override
    public Flowable<List<Book>> getWithObservable() {

        return Flowable.create(new FlowableOnSubscribe<List<Book>>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<List<Book>> e) throws Exception {
                List<Book> list = mService.listBooks()
                        .execute()
                        .body();
                e.onNext(list);
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER);
    }
}
