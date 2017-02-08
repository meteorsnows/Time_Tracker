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
import android.widget.Toast;

/**
 * Created by Elliot on 2/5/2017.
 */

public class CategoryAddFragment extends Fragment implements OnClickListener {

    DatabaseHelper timeDatabase;
    EditText newCategoryEditText;
    Spinner parentEditSpinner;
    Button createButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_category_add, container, false);

        newCategoryEditText = (EditText) v.findViewById(R.id.newCategoryEditText);
        createButton = (Button) v.findViewById(R.id.createButton);
        parentEditSpinner = (Spinner) v.findViewById(R.id.parentEditSpinner);
        timeDatabase = new DatabaseHelper(getActivity());

        createButton.setOnClickListener(this);

        updateParentSelectorItems();

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createButton:

                Integer parentID = Long.valueOf(parentEditSpinner.getSelectedItemId()).intValue();
                Toast.makeText(getActivity(), parentID, Toast.LENGTH_LONG).show();
                boolean errorCheck = timeDatabase.addDataCategories(newCategoryEditText.getText().toString(), parentID);

                if (errorCheck){
                    Toast.makeText(getActivity(),"Saved!", Toast.LENGTH_LONG).show();
                    newCategoryEditText.setText("");
                } else {
                    Toast.makeText(getActivity(),"Something went wrong :-(", Toast.LENGTH_LONG).show();
                }
                updateParentSelectorItems();
                break;

        }
    }


    public void updateParentSelectorItems(){
        SparseArray<String> categoryNames = timeDatabase.getColumnData("categories", 1);
        categoryNames.put(0, "None");
        SparseStringsAdapter spinnerAdapter = new SparseStringsAdapter(getActivity(), categoryNames);
        parentEditSpinner.setAdapter(spinnerAdapter);
        parentEditSpinner.setSelection(0);
    }
}