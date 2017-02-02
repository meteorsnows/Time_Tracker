package com.elliot_labs.timetracker;


import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements OnClickListener {

    Button toggleButton;
    Button resetButton;
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
        resetButton = (Button) v.findViewById(R.id.buttonReset);
        chronometer = (Chronometer) v.findViewById(R.id.mainChronometer);

        toggleButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);

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

        return v;
    }

    public void toggleChronometer(){
        if(!currentlyTiming){
            chronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
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
    public void resetChronometer(){
        timeWhenStopped = 0;
        chronometer.setBase(SystemClock.elapsedRealtime());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonToggleChronometer:
                toggleChronometer();
                break;
            case R.id.buttonReset:
                resetChronometer();
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