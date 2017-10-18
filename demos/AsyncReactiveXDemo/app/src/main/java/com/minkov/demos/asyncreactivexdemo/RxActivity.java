package com.minkov.demos.asyncreactivexdemo;

import android.os.Bundle;

import com.minkov.demos.asyncreactivexdemo.base.BaseActivity;
import com.minkov.demos.asyncreactivexdemo.models.Book;
import com.minkov.demos.asyncreactivexdemo.repos.BooksRepository;
import com.minkov.demos.asyncreactivexdemo.services.BooksService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxActivity extends BaseActivity {

    private BooksRepository mBooksRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_rx);
        super.onCreate(savedInstanceState);

        BooksService booksService = ((BooksApplication) getApplication())
                .getBooksService();

        mBooksRepository = new BooksRepository(booksService);

        mBooksRepository.getWithObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Book>>() {
                    @Override
                    public void accept(List<Book> books) throws Exception {
                        updateUI(books);
                    }
                });

    }
}
