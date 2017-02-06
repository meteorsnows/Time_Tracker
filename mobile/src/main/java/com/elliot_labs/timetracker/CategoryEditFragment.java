package com.elliot_labs.timetracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by Elliot on 2/5/2017.
 */

public class CategoryEditFragment extends Fragment implements OnClickListener {

    EditText editCategoryEditText;
    Button saveButton;
    Spinner parentEditSpinner, editSpinner;
    DatabaseHelper timeDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_category_edit, container, false);

        saveButton = (Button) v.findViewById(R.id.saveButton);
        editCategoryEditText = (EditText) v.findViewById(R.id.editCategoryEditText);
        parentEditSpinner = (Spinner) v.findViewById(R.id.parentEditSpinner);
        editSpinner = (Spinner) v.findViewById(R.id.editSpinner);

        timeDatabase = new DatabaseHelper(getActivity());

        saveButton.setOnClickListener(this);

        updateEditSpinnerItems();
        updateParentSpinnerItems();

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveButton:
                String selectedId = Long.toString(editSpinner.getSelectedItemId());

                // code

                updateEditSpinnerItems();
                break;

        }
    }

    public void updateEditSpinnerItems(){
        SparseArray<String> categoryNames = timeDatabase.getColumnData("categories", 1);
        SparseStringsAdapter spinnerAdapter = new SparseStringsAdapter(getActivity(), categoryNames);
        editSpinner.setAdapter(spinnerAdapter);
    }

    public void updateParentSpinnerItems(){
        SparseArray<String> categoryNames = timeDatabase.getColumnData("categories", 1);
        SparseStringsAdapter spinnerAdapter = new SparseStringsAdapter(getActivity(), categoryNames);
        parentEditSpinner.setAdapter(spinnerAdapter);
    }
}
