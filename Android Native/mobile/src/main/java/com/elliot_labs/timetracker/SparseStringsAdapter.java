package com.elliot_labs.timetracker;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Used to allow sparse string arrays on spinners.
 */

public class SparseStringsAdapter extends SparseArrayAdapter<String> {

    private final LayoutInflater mInflater;

    public SparseStringsAdapter(Context context, SparseArray<String> data) {
        mInflater = LayoutInflater.from(context);
        setData(data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView result = (TextView) convertView;
        if (result == null) {
            result = (TextView) mInflater.inflate(android.R.layout.simple_list_item_1, null);
        }
        result.setText(getItem(position));
        return result;
    }
}
