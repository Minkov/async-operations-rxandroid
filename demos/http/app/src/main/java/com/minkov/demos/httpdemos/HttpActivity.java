package com.minkov.demos.httpdemos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpActivity extends AppCompatActivity {

    private HttpRequester mHttpRequester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        mHttpRequester = new HttpRequester();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PersonsService service = retrofit.create(PersonsService.class);

        Call<List<Person>> persons = service.listPersons();
        persons.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                List<Person> p = response.body();
                Toast.makeText(HttpActivity.this, p.get(0).getName(), Toast.LENGTH_LONG)
                        .show();
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                t.printStackTrace();
            }
        });

//        load();
    }

    private void load() {
        final String URL = "";
        String body = "";

        mHttpRequester.post(URL, body)
                .switchMap(result -> mHttpRequester.get(URL))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result ->
                        Toast.makeText(HttpActivity.this, "Item created!", Toast.LENGTH_SHORT)
                             .show());

        mHttpRequester.get(URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String body) throws Exception {
                        // parse body...
                    }
                });
    }
}
