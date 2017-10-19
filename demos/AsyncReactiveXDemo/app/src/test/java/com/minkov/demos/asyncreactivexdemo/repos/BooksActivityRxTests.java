package com.minkov.demos.asyncreactivexdemo.repos;

import com.minkov.demos.asyncreactivexdemo.models.Book;
import com.minkov.demos.asyncreactivexdemo.services.BooksService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import io.reactivex.subscribers.TestSubscriber;
import retrofit2.Call;
import retrofit2.Response;

import static org.mockito.Mockito.when;

public class BooksActivityRxTests {
    @Mock
    BooksService mockBooksService;

    @Mock
    Call<List<Book>> mockCall;

    private List<Book> mBooks = Arrays.asList(new Book("1", "Test title #1", "Description #1"),
            new Book("1", "Test title #2", "Description #2"),
            new Book("1", "Test title #3", "Description #3"),
            new Book("1", "Test title #4", "Description #4"),
            new Book("1", "Test title #5", "Description #5"));


    private BooksRepository repository;

    @Before
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);
        when(mockCall.execute())
                .thenReturn(Response.success(mBooks));

        when(mockBooksService.listBooks())
                .thenReturn(mockCall);

        repository = new BooksRepository(mockBooksService);
    }

    @Test
    public void when_books_available_show_return_books() throws InterruptedException {
        TestSubscriber subscriber = new TestSubscriber();
        repository.getWithObservable()
                .subscribe(subscriber);

        subscriber.assertValue(mBooks);
    }

}
