package com.elliot_labs.timetracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */

public class CategoryFragment extends Fragment implements OnClickListener {

    EditText nameCategory;
    Spinner parentSelector;
    TextView saveHeaderText;
    DatabaseHelper timeDatabase;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_category, container, false);

        nameCategory = (EditText) v.findViewById(R.id.nameCategory);
        parentSelector = (Spinner) v.findViewById(R.id.parentSelector);
        saveHeaderText = (TextView) v.findViewById(R.id.textView6);
        timeDatabase = new DatabaseHelper(getActivity());

        Button saveCategoryButton = (Button) v.findViewById(R.id.saveCategoryButton);

        saveCategoryButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveCategoryButton:
                boolean errorCheck = timeDatabase.addDataCategories(nameCategory.getText().toString(), null);
                if (errorCheck){
                    saveHeaderText.setText("Saved!");
                } else {
                    saveHeaderText.setText("Something went wrong :-(");
                }
                break;
        }
    }
}
