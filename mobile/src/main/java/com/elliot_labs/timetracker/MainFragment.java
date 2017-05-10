package com.elliot_labs.timetracker;


import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Spinner;


/**
 * The main screen that is used by the user.
 * This screen will create timer(s) that the user will interact with.
 */

public class MainFragment extends Fragment implements OnClickListener {

    Button toggleButton;
    DatabaseHelper timeDatabase;
    Button pauseButton;
    Spinner categorySpinner;
    Chronometer chronometer;
    long timeWhenStopped = 0;
    boolean currentlyTiming = false;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        toggleButton = (Button) v.findViewById(R.id.buttonToggleChronometer);
        pauseButton = (Button) v.findViewById(R.id.buttonPause);
        categorySpinner = (Spinner) v.findViewById(R.id.categorySpinner);
        chronometer = (Chronometer) v.findViewById(R.id.mainChronometer);
        timeDatabase = new DatabaseHelper(getActivity());

        toggleButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);

        // Reads the categories data from the database and populates the spinner with the data.
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //do something here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // do nothing
            }
        });

        if (savedInstanceState != null) {
            chronometer.setBase(savedInstanceState.getLong("time"));
            if (savedInstanceState.getBoolean("isTiming")) {
                chronometer.start();
                toggleButton.setText("Stop");
                currentlyTiming = savedInstanceState.getBoolean("isTiming");
            } else {
                chronometer.setBase(SystemClock.elapsedRealtime() + savedInstanceState.getLong("stopTime"));
                timeWhenStopped = savedInstanceState.getLong("stopTime");
            }
        }

        updateSpinnerItems();

        return v;
    }

    public void toggleChronometer() {
        if (!currentlyTiming) {
            loadTimingState(categorySpinner.getSelectedItem().toString());
            chronometer.start();
            toggleButton.setText("Stop");
            currentlyTiming = true;
        } else {
            timeWhenStopped = chronometer.getBase() - SystemClock.elapsedRealtime();
            chronometer.stop();
            toggleButton.setText("Start");
            currentlyTiming = false;
        }
    }

    public void pauseTime() {
        timeWhenStopped = 0;
        chronometer.setBase(SystemClock.elapsedRealtime());
    }

    public void updateSpinnerItems() {
        SparseArray<String> timingCategory = timeDatabase.getColumnStringData("categories", "name");
        SparseStringsAdapter spinnerAdapter = new SparseStringsAdapter(getActivity(), timingCategory);
        categorySpinner.setAdapter(spinnerAdapter);
        timingCategory.put(0, "None");
    }

    public void loadTimingState(String categoryName) {
        SparseArray<String> categoryNameArray = timeDatabase.getColumnStringData("categories", "name");
        SparseArray<Long> timingFrom = timeDatabase.getColumnLongData("currently_timing", "timing_from");
        SparseArray<Integer> timingCategory = timeDatabase.getColumnIntegerData("currently_timing", "category");

        if (categoryName == "None") {
            Integer idOfTimingFrom = timingCategory.indexOfValue(0);
            if (timingFrom.get(idOfTimingFrom) == null) {
                chronometer.setBase(SystemClock.elapsedRealtime());
            } else {
                chronometer.setBase(timingFrom.get(idOfTimingFrom));
            }
        } else {
            Integer idOfCategory = categoryNameArray.indexOfValue(categoryName);
            Integer idOfTimingFrom = timingCategory.indexOfValue(idOfCategory);
            chronometer.setBase(timingFrom.get(idOfTimingFrom));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonToggleChronometer:
                toggleChronometer();
                break;
            case R.id.buttonPause:
                pauseTime();
                break;
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Save myVar's value in saveInstanceState bundle
        outState.putLong("time", chronometer.getBase());
        outState.putBoolean("isTiming", currentlyTiming);
        outState.putLong("stopTime", timeWhenStopped);

        super.onSaveInstanceState(outState);
    }
}