package com.minkov.demos.httpdemos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by minkov on 10/18/17.
 */

public interface PersonsService {
    @GET(Constants.PEOPLE_ROUTE)
    Call<List<Person>> listPersons();
}
