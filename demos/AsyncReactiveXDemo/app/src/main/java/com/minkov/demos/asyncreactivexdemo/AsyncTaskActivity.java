package com.minkov.demos.asyncreactivexdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.minkov.demos.asyncreactivexdemo.base.BaseActivity;
import com.minkov.demos.asyncreactivexdemo.models.Book;
import com.minkov.demos.asyncreactivexdemo.repos.BooksRepository;
import com.minkov.demos.asyncreactivexdemo.repos.base.BaseRepository;
import com.minkov.demos.asyncreactivexdemo.services.BooksService;

import java.util.List;

public class AsyncTaskActivity extends BaseActivity {
    private BooksRepository mBooksRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_async_tasks);
        super.onCreate(savedInstanceState);

        BooksService booksService = ((BooksApplication) getApplication())
                .getBooksService();

        mBooksRepository = new BooksRepository(booksService);
        mBooksRepository.getWithAsyncTask(new BaseRepository.OnDataReceivedListener<Book>() {
            @Override
            public void onSuccess(final List<Book> list) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI(list);
                    }
                });
            }

            @Override
            public void onError(final Throwable ex) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showError(ex);
                    }
                });
            }
        });
    }
}
