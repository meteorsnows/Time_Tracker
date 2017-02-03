package com.elliot_labs.timetracker;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
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
    TextView categoryListText;
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
        categoryListText = (TextView) v.findViewById(R.id.listOfCategories);
        timeDatabase = new DatabaseHelper(getActivity());

        categoryListText.setMovementMethod(new ScrollingMovementMethod());

        Button saveCategoryButton = (Button) v.findViewById(R.id.saveCategoryButton);
        Button refreshListButton = (Button) v.findViewById(R.id.refreshListButton);

        saveCategoryButton.setOnClickListener(this);
        refreshListButton.setOnClickListener(this);

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

            case R.id.refreshListButton:
                Cursor categories = timeDatabase.listCategories();
                if (categories.getCount() == 0) {
                    categoryListText.setText("Nothing to show here...");
                    break;
                } else {
                    StringBuffer buffer = new StringBuffer();
                    while(categories.moveToNext()) {
                        buffer.append("ID :" + categories.getString(0) + "\n" );
                        buffer.append("Name :" + categories.getString(1) + "\n" );
                        buffer.append("Parent :" + categories.getString(2) + "\n\n" );

                    }
                    categoryListText.setText(buffer.toString());
                }
        }
    }
}
