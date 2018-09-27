package com.elliot_labs.timetracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */

public class CategoryFragment extends Fragment {

    FragmentTabHost mTabHost;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.layout.fragment_category);

        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Add"),
                CategoryAddFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("Edit"),
                CategoryEditFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator("Delete"),
                CategoryDeleteFragment.class, null);

        return mTabHost;
    }
}