package com.elliot_labs.timetracker;


import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements OnClickListener {


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        Button toastButton = (Button) v.findViewById(R.id.toastButton);
        Button notificationButton = (Button) v.findViewById(R.id.notificationButton);

        toastButton.setOnClickListener(this);
        notificationButton.setOnClickListener(this);
        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toastButton:
                ProgressDialog dialog = new ProgressDialog(getActivity());
                dialog.setMessage("test");
                dialog.setCancelable(false);
                dialog.setInverseBackgroundForced(false);
                dialog.show();
                break;

            case R.id.notificationButton:
                Context contextNotification = getActivity().getApplicationContext();
                NotificationCompat.Builder mBuilder = (android.support.v7.app.NotificationCompat.Builder)
                        new NotificationCompat.Builder(contextNotification)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("My notification")
                                .setContentText("Hello World!");
                NotificationManager mNotificationManager =
                        (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(0, mBuilder.build());

                break;
        }
    }
}
