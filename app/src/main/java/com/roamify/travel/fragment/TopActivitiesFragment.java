package com.roamify.travel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roamify.travel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopActivitiesFragment extends Fragment {
    RecyclerView rvTopActivities;
    private View rootView;
    public TopActivitiesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_top_activities, container, false);
        initView(rootView);

        return rootView;
    }
    private void initView(View rootView) {
        rvTopActivities = (RecyclerView) rootView.findViewById(R.id.rv_topActivities);
    }


}
