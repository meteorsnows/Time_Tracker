package com.elliot_labs.timetracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Elliot on 2/5/2017.
 */

public class CategoryEditFragment extends Fragment implements OnClickListener {

    EditText editCategoryEditText;
    Button saveButton;
    Spinner editSpinner;
//    Spinner parentEditSpinner;
    DatabaseHelper timeDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_category_edit, container, false);

        saveButton = (Button) v.findViewById(R.id.saveButton);
        editCategoryEditText = (EditText) v.findViewById(R.id.editCategoryEditText);
//        parentEditSpinner = (Spinner) v.findViewById(R.id.parentEditSpinner);
        editSpinner = (Spinner) v.findViewById(R.id.editSpinner);

        timeDatabase = new DatabaseHelper(getActivity());

        saveButton.setOnClickListener(this);

        editSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                SparseArray<String> text_name = timeDatabase.getColumnData("categories", 1);
                Long LongID = id;
                Integer IntegerID = LongID.intValue();
                editCategoryEditText.setText(text_name.get(IntegerID));
                updateSpinnerItems();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // do nothing
            }

        });

        updateSpinnerItems();

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveButton:
                Integer editID = (int) (long) editSpinner.getSelectedItemId();

//                Long parentLong = parentEditSpinner.getSelectedItemId();
//                Integer parent_data = parentLong != null ? parentLong.intValue() : null;

                boolean errorCheckName = timeDatabase.updateDataByID("categories", editID, "name", editCategoryEditText.getText().toString());
//                boolean errorCheckParent = timeDatabase.updateDataByID("categories", editID, "parent", parent_data.toString());

                if (errorCheckName){
                    Toast.makeText(getActivity(),"Saved!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(),"Something went wrong :-(", Toast.LENGTH_LONG).show();
                }

                updateSpinnerItems();
                break;

        }
    }

    public void updateSpinnerItems(){
        SparseArray<String> categoryNames = timeDatabase.getColumnData("categories", 1);
//        SparseArray<String> parentNames = timeDatabase.getColumnData("categories", 2);

        SparseStringsAdapter spinnerAdapter = new SparseStringsAdapter(getActivity(), categoryNames);
        editSpinner.setAdapter(spinnerAdapter);

//        categoryNames.put(0, "None");
//
//        parentEditSpinner.setAdapter(spinnerAdapter);
//
//
//        for (int i = 0; i < parentEditSpinner.getCount(); i++) {
//            if (parentEditSpinner.getItemAtPosition(i).equals("value")) {
//                parentEditSpinner.setSelection(i);
//                break;
//            }
//        }

    }
}
