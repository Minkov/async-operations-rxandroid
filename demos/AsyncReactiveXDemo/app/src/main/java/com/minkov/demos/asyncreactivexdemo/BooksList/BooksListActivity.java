package com.minkov.demos.asyncreactivexdemo.BooksList;

import android.os.Bundle;

import com.minkov.demos.asyncreactivexdemo.BooksApplication;
import com.minkov.demos.asyncreactivexdemo.R;
import com.minkov.demos.asyncreactivexdemo.base.BaseActivity;
import com.minkov.demos.asyncreactivexdemo.factories.SchedulersFactoryImpl;
import com.minkov.demos.asyncreactivexdemo.models.Book;
import com.minkov.demos.asyncreactivexdemo.repos.BooksRepository;
import com.minkov.demos.asyncreactivexdemo.services.BooksService;

import java.util.List;

public class BooksListActivity extends BaseActivity implements BooksListContracts.View {

    private BooksListContracts.Presenter mPresenter;
    private BooksRepository mRepository;
    private BooksService mBooksService;
    private SchedulersFactoryImpl mSchedulersFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_books_list);
        super.onCreate(savedInstanceState);

        mBooksService = ((BooksApplication) getApplication())
                .getBooksService();
        mRepository = new BooksRepository(mBooksService);
        mSchedulersFactory = new SchedulersFactoryImpl();

        BooksListContracts.Presenter presenter = new BooksListPresenter(mRepository, mSchedulersFactory);
        setPresenter(presenter);
    }

    @Override
    public void setPresenter(BooksListContracts.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void update(List<Book> books) {
        super.updateUI(books);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.subscribe(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }
}
