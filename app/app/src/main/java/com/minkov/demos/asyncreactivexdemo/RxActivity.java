package com.minkov.demos.asyncreactivexdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.minkov.demos.asyncreactivexdemo.data.PersonsData;
import com.minkov.demos.asyncreactivexdemo.data.models.Person;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RxActivity extends AppCompatActivity {

    private PeoplesAdapter mAdapter;
    private ListView mListViewPeople;
    private PersonsData mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);
        mAdapter = new PeoplesAdapter(this, android.R.layout.simple_list_item_1);
        mListViewPeople = (ListView) findViewById(R.id.lv_people);
        mListViewPeople.setAdapter(mAdapter);

        mData = new PersonsData();
        load();
    }

    private void load() {
        mData.getPersons()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Person[]>() {
                    @Override
                    public void accept(Person[] persons) throws Exception {
                        mAdapter.clear();
                        mAdapter.addAll(persons);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(RxActivity.this, String.format("Error: %s", throwable.getStackTrace()), Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }


}
