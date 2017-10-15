package com.minkov.demos.asyncreactivexdemo.data;

import android.os.AsyncTask;

import com.minkov.demos.asyncreactivexdemo.Constrants;
import com.minkov.demos.asyncreactivexdemo.data.models.Person;
import com.minkov.demos.asyncreactivexdemo.http.HttpRequester;
import com.minkov.demos.asyncreactivexdemo.utils.JsonParser;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by minkov on 10/15/17.
 */

public class PersonsData {
    private final JsonParser<Person[]> mParser;
    private final HttpRequester mHttpRequester;

    public PersonsData() {
        this(new JsonParser<>(Person[].class), new HttpRequester());
    }

    public PersonsData(JsonParser<Person[]> parser, HttpRequester httpRequester) {
        mParser = parser;
        mHttpRequester = httpRequester;
    }

    public Observable<Person[]> getPersons() {
        return Observable.create(new ObservableOnSubscribe<Person[]>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Person[]> e) throws Exception {
                String json = mHttpRequester.getFrom(Constrants.API_ENDPOINT);
                Person[] persons = mParser.parse(json);
                e.onNext(persons);
                e.onComplete();
            }
        });
    }

    public void getPersons(final OnPersonsDataListener onPersonsDataListener) {
        AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                try {
                    String json = mHttpRequester.getFrom(strings[0]);
                    Person[] persons = mParser.parse(json);
//                    onPersonsDataListener.onSuccess(persons);
                    onPersonsDataListener.onSuccess(new Person[]{});
                } catch (IOException e) {
                    onPersonsDataListener.onError(e);
                }
                return null;
            }
        };

        task.execute(Constrants.API_ENDPOINT);
    }


    public interface OnPersonsDataListener {
        void onSuccess(Person[] persons);

        void onError(Exception ex);
    }
}
