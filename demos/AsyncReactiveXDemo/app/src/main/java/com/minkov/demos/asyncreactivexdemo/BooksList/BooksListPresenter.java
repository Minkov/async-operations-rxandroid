package com.minkov.demos.asyncreactivexdemo.BooksList;

import com.minkov.demos.asyncreactivexdemo.factories.SchedulersFactory;
import com.minkov.demos.asyncreactivexdemo.models.Book;
import com.minkov.demos.asyncreactivexdemo.repos.BooksRepository;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by minkov on 10/18/17.
 */

public class BooksListPresenter implements BooksListContracts.Presenter {
    private final BooksRepository mRepository;
    private final SchedulersFactory mSchedulersFactory;
    private BooksListContracts.View mView;

    public BooksListPresenter(BooksRepository repository, SchedulersFactory schedulersFactory) {
        mRepository = repository;
        mSchedulersFactory = schedulersFactory;
    }

    private void startLoading() {
        mRepository.getWithObservable()
                .subscribeOn(mSchedulersFactory.io())
                .observeOn(mSchedulersFactory.ui())
                .subscribe(new Consumer<List<Book>>() {
                    @Override
                    public void accept(List<Book> books) throws Exception {
                        mView.update(books);
                    }
                });
    }

    @Override
    public void subscribe(BooksListContracts.View view) {
        mView = view;
        startLoading();
    }

    @Override
    public void unsubscribe() {
        mView = null;
    }
}
