package com.minkov.demos.asyncreactivexdemo.BooksList;

import com.minkov.demos.asyncreactivexdemo.models.Book;

import java.util.List;

/**
 * Created by minkov on 10/18/17.
 */

public interface BooksListContracts {
    public interface View {
        void setPresenter(Presenter presenter);

        void update(List<Book> books);
    }

    public interface Presenter {
        void subscribe(View view);

        void unsubscribe();
    }
}
