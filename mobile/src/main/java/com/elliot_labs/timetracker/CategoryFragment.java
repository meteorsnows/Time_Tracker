package com.elliot_labs.timetracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */

public class CategoryFragment extends Fragment implements OnClickListener {

    EditText nameCategory;
    Spinner deleteSelector;
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
        deleteSelector = (Spinner) v.findViewById(R.id.deleteSelector);
        categoryListText = (TextView) v.findViewById(R.id.listOfCategories);
        timeDatabase = new DatabaseHelper(getActivity());

        categoryListText.setMovementMethod(new ScrollingMovementMethod());

        Button saveCategoryButton = (Button) v.findViewById(R.id.saveCategoryButton);
        Button refreshListButton = (Button) v.findViewById(R.id.refreshListButton);
        Button deleteCategoryButton = (Button) v.findViewById(R.id.deleteButton);

        saveCategoryButton.setOnClickListener(this);
        refreshListButton.setOnClickListener(this);
        deleteCategoryButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveCategoryButton:
                boolean errorCheck = timeDatabase.addDataCategories(nameCategory.getText().toString(), null);
                if (errorCheck){
                    Toast.makeText(getActivity(),"Saved!", Toast.LENGTH_LONG).show();
                    nameCategory.setText("");
                } else {
                    Toast.makeText(getActivity(),"Something went wrong :-(", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.refreshListButton:
                SparseArray<String> categoryNames = timeDatabase.getColumnData("categories", 1);
                SparseArray<String> categoryParents = timeDatabase.getColumnData("categories", 2);
                updateDeleteSelectorItems();

                if (categoryNames.size() == 0) {
                    categoryListText.setText("Nothing to show here...");
                    break;
                } else {
                    StringBuffer buffer = new StringBuffer();
                    String parent = "";

                    for(int i = 0; i < categoryNames.size(); i++) {
                        int key = categoryNames.keyAt(i);
                        // get the object by the key.
                        String name = categoryNames.get(key);
                        Integer keyInteger = key;
                        String parentData = categoryParents.get(key);

                        if (parentData == null) {
                            parent = "None";
                        } else {
                            parent = parentData;
                        }

                        buffer.append("ID: " + keyInteger.toString() + "\n" );
                        buffer.append("Name: " + name + "\n" );
                        buffer.append("Parent: " + parent + "\n\n");
                    }
                    categoryListText.setText(buffer.toString());
                }
                break;
            case R.id.deleteButton:
                String selectedId = Integer.toString(deleteSelector.getSelectedItemPosition());

                Toast.makeText(getActivity(), selectedId, Toast.LENGTH_LONG).show();
        }
    }

    public void updateDeleteSelectorItems(){
        SparseArray<String> categoryNames = timeDatabase.getColumnData("categories", 1);
        SparseStringsAdapter spinnerAdapter = new SparseStringsAdapter(getActivity(), categoryNames);
        deleteSelector.setAdapter(spinnerAdapter);
    }
}