package com.elliot_labs.timetracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Elliot on 2/5/2017.
 */

public class CategoryDeleteFragment extends Fragment implements OnClickListener {

    Button deleteButton;
    Spinner deleteSpinner;
    DatabaseHelper timeDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_category_delete, container, false);

        deleteButton = (Button) v.findViewById(R.id.deleteButton);
        deleteSpinner = (Spinner) v.findViewById(R.id.deleteSpinner);
        timeDatabase = new DatabaseHelper(getActivity());

        deleteButton.setOnClickListener(this);

        updateDeleteSelectorItems();

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deleteButton:
                String selectedId = Long.toString(deleteSpinner.getSelectedItemId());

                if (timeDatabase.deleteByID("categories", selectedId) == 1) {
                    Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Something went wrong :-(", Toast.LENGTH_LONG).show();
                }

                updateDeleteSelectorItems();
                break;

        }
    }


    public void updateDeleteSelectorItems(){
        SparseArray<String> categoryNames = timeDatabase.getColumnData("categories", 1);
        SparseStringsAdapter spinnerAdapter = new SparseStringsAdapter(getActivity(), categoryNames);
        deleteSpinner.setAdapter(spinnerAdapter);
    }
}