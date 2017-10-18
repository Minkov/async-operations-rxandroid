package com.minkov.demos.asyncreactivexdemo.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.minkov.demos.asyncreactivexdemo.AsyncTaskActivity;
import com.minkov.demos.asyncreactivexdemo.BooksList.BooksListActivity;
import com.minkov.demos.asyncreactivexdemo.R;
import com.minkov.demos.asyncreactivexdemo.RxActivity;
import com.minkov.demos.asyncreactivexdemo.models.Book;

import java.util.List;

/**
 * Created by minkov on 10/18/17.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private static final String CURRENT_ACTIVITY_IDENTIFIER = "CURRENT_ACTIVIY";
    private ListView mListView;
    private ArrayAdapter<Book> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new ArrayAdapter<Book>(this, android.R.layout.simple_list_item_1);
        mListView = (ListView) findViewById(R.id.lv);
        mListView.setAdapter(mAdapter);

        setupDrawer();
    }

    protected void setupDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        long currentActivityIdentifier = getIntent().getLongExtra(CURRENT_ACTIVITY_IDENTIFIER, 1);
        new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withSelectedItem(currentActivityIdentifier)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName("AsyncTask demo")
                                .withIdentifier(1),
                        new PrimaryDrawerItem()
                                .withName("RxAndroid demo")
                                .withIdentifier(2),
                        new PrimaryDrawerItem()
                                .withName("MVP")
                                .withIdentifier(3)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Intent intent = null;
                        if (drawerItem.getIdentifier() == 1) {
                            intent = new Intent(BaseActivity.this, AsyncTaskActivity.class);
                        } else if (drawerItem.getIdentifier() == 2) {
                            intent = new Intent(BaseActivity.this, RxActivity.class);
                        } else if (drawerItem.getIdentifier() == 3) {
                            intent = new Intent(BaseActivity.this, BooksListActivity.class);
                        }

                        if (intent == null) {
                            return false;
                        }

                        intent.putExtra(CURRENT_ACTIVITY_IDENTIFIER, drawerItem.getIdentifier());
                        startActivity(intent);
                        return true;
                    }
                }).build();
    }

    protected void updateUI(List<Book> bookList) {
        mAdapter.clear();
        mAdapter.addAll(bookList);
    }

    protected void showError(Throwable ex) {
        Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT)
                .show();
    }
}
