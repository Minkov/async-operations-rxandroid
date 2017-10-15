package com.minkov.demos.asyncreactivexdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.minkov.demos.asyncreactivexdemo.data.PersonsData;
import com.minkov.demos.asyncreactivexdemo.data.models.Person;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PersonsData.OnPersonsDataListener {

    private ListView mListViewPeople;
    private PeoplesAdapter mAdapter;
    private Button mBtnShowRxActivity;
    private PersonsData mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapter = new PeoplesAdapter(this, android.R.layout.simple_list_item_1);
        mListViewPeople = (ListView) findViewById(R.id.lv_people);
        mListViewPeople.setAdapter(mAdapter);
        mBtnShowRxActivity = (Button) findViewById(R.id.btn_show_activity);
        mBtnShowRxActivity.setOnClickListener(this);

        mData = new PersonsData();
        mData.getPersons(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, RxActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSuccess(final Person[] persons) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.clear();
                mAdapter.addAll(persons);
            }
        });
    }

    @Override
    public void onError(Exception ex) {
        Toast.makeText(this, String.format("Error in response: %s", ex.getStackTrace()), Toast.LENGTH_SHORT)
                .show();
    }
}
