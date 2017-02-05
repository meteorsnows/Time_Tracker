package com.elliot_labs.timetracker;

import android.util.SparseArray;
import android.widget.BaseAdapter;

/**
 * Created by Elliot on 2/5/2017.
 */

public abstract class SparseArrayAdapter<E> extends BaseAdapter {

    private SparseArray<E> mData;

    public void setData(SparseArray<E> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public E getItem(int position) {
        return mData.valueAt(position);
    }

    @Override
    public long getItemId(int position) {
        return mData.keyAt(position);
    }
}
