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
 * Delete's the selected category entry in the database and updates the spinner.
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

    // When the delete button is clicked, gets the currently selected item and tells the database to
    // delete the selected item.
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deleteButton:
                Long selectedIDLong = deleteSpinner.getSelectedItemId();
                Integer selectedId = selectedIDLong.intValue();

                if (timeDatabase.deleteRowByID("categories", selectedId)) {
                    Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Something went wrong :-(", Toast.LENGTH_LONG).show();
                }

                updateDeleteSelectorItems();
                break;
        }
    }

    // Updates the spinner that shows each category to delete by grabbing a copy of the latest data from
    // the database and populating the spinner with it.
    public void updateDeleteSelectorItems() {
        SparseArray<String> categoryNames = timeDatabase.getColumnStringData("categories", "name");
        SparseStringsAdapter spinnerAdapter = new SparseStringsAdapter(getActivity(), categoryNames);
        deleteSpinner.setAdapter(spinnerAdapter);
    }
}