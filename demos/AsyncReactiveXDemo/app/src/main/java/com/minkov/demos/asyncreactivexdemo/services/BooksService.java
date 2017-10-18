package com.minkov.demos.asyncreactivexdemo.services;

import com.minkov.demos.asyncreactivexdemo.Constants;
import com.minkov.demos.asyncreactivexdemo.models.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by minkov on 10/18/17.
 */

public interface BooksService {
    @GET(Constants.BOOKS_ROUTE)
    Call<List<Book>> listBooks();
}
