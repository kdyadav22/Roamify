package com.roamify.travel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roamify.travel.R;
import com.roamify.travel.adapters.ActivitiesPackageListRVAdapter;
import com.roamify.travel.adapters.TopPackageListRVAdapter;
import com.roamify.travel.models.RawDataModel;
import com.roamify.travel.rawdata.RawData;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopActivitiesFragment extends Fragment {
    RecyclerView rvTopActivities;
    private View rootView;
    RawDataModel rawDataModel = new RawDataModel();
    public TopActivitiesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_top_activities, container, false);
        initView(rootView);
        try {
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
            //rvTopActivities.setLayoutManager(mLayoutManager);
            rvTopActivities.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

            rvTopActivities.setHasFixedSize(true);
            rvTopActivities.setItemAnimator(new DefaultItemAnimator());
            rvTopActivities.setAdapter(new TopPackageListRVAdapter(RawData.setPackage(), getActivity(),""));

        } catch (InflateException ie) {
            ie.getMessage();
        }
        return rootView;
    }
    private void initView(View rootView) {
        rvTopActivities = (RecyclerView) rootView.findViewById(R.id.rv_topActivities);
    }


}
