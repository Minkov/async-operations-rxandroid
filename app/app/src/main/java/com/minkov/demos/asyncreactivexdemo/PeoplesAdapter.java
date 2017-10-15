package com.minkov.demos.asyncreactivexdemo;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.minkov.demos.asyncreactivexdemo.data.models.Person;

/**
 * Created by minkov on 10/15/17.
 */

public class PeoplesAdapter extends ArrayAdapter<Person> {
    public PeoplesAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView view = (convertView == null || !(convertView instanceof TextView))
                ? new TextView(getContext())
                : (TextView) convertView;

        view.setText(getItem(position).getName());

        return view;
    }
}
