package com.minkov.demos.asyncreactivexdemo.data;

import com.minkov.demos.asyncreactivexdemo.Constrants;
import com.minkov.demos.asyncreactivexdemo.data.models.Person;
import com.minkov.demos.asyncreactivexdemo.http.HttpRequester;
import com.minkov.demos.asyncreactivexdemo.utils.JsonParser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PersonsDataAsyncTaskTests {
    @Mock
    HttpRequester mockHttpRequester;

    @Mock
    JsonParser<Person[]> mockParser;

    String json = "IT works!";
    Person[] persons = new Person[]{};

    @Before
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);
        when(mockHttpRequester.getFrom(Constrants.API_ENDPOINT))
                .thenReturn(json);
        when(mockParser.parse(json))
                .thenReturn(persons);
    }

    @Test
    public void getPersons() throws IOException {
        PersonsData personsData = new PersonsData(mockParser, mockHttpRequester);

        personsData.getPersons(new PersonsData.OnPersonsDataListener() {
            @Override
            public void onSuccess(Person[] persons) {
                try {
                    verify(mockHttpRequester).getFrom(Constrants.API_ENDPOINT);
                } catch (IOException e) {
                }
                verify(mockParser).parse(json);
            }

            @Override
            public void onError(Exception ex) {

            }
        });
    }

}
