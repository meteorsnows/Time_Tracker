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

        editSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                SparseArray<String> text_name = timeDatabase.getColumnData("categories", 1);
                Long LongID = id;
                Integer IntegerID = LongID.intValue();
                editCategoryEditText.setText(text_name.get(IntegerID));
                updateParentSpinnerItems();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // do nothing
            }

        });

        updateEditSpinnerItems();
        updateParentSpinnerItems();

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveButton:
                Long editLongSelectedId = editSpinner.getSelectedItemId();
                Integer editSelectedId = editLongSelectedId.intValue();

                Long parentLong = parentEditSpinner.getSelectedItemId();
                Integer parent_data = parentLong != null ? parentLong.intValue() : null;

                boolean errorCheckName = timeDatabase.updateDataByID("categories", editSelectedId, "name", editCategoryEditText.getText().toString());
                boolean errorCheckParent = timeDatabase.updateDataByID("categories", editSelectedId, "parent", parent_data.toString());

                if (errorCheckName && errorCheckParent){
                    Toast.makeText(getActivity(),"Saved!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(),"Something went wrong :-(", Toast.LENGTH_LONG).show();
                }

                updateEditSpinnerItems();
                updateParentSpinnerItems();
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
        categoryNames.put(0, "None");
        parentEditSpinner.setAdapter(spinnerAdapter);

        SparseArray<String> parentNames = timeDatabase.getColumnData("categories", 2);

        Integer parent_data;
        Long parentLong;

        if(editSpinner != null && editSpinner.getSelectedItem() !=null ) {
            parentLong = editSpinner.getSelectedItemId();
            parent_data = parentLong.intValue();
            parentEditSpinner.setSelection(Integer.parseInt(parentNames.get(parent_data)));
        } else  {
            parentEditSpinner.setSelection(0);
        }
    }
}
