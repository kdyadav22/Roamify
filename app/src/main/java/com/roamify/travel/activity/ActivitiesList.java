package com.roamify.travel.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.roamify.travel.R;
import com.roamify.travel.adapters.ActivitiesRVAdapter;
import com.roamify.travel.adapters.MenuGridRVAdapter;
import com.roamify.travel.listeners.ActivityItemClickListener;
import com.roamify.travel.listeners.MenuItemClickListener;
import com.roamify.travel.models.RawDataModel;
import com.roamify.travel.rawdata.RawData;

public class ActivitiesList extends AppCompatActivity implements ActivityItemClickListener {

    public static ActivityItemClickListener activityItemClickListener;
    static ActivitiesList mInstance;
    private RecyclerView mRecyclerView;

    public static synchronized ActivitiesList getInstance() {
        return mInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_list);
        activityItemClickListener = this;
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recyclerView);
        try {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

            //RawDataModel rawDataModel = (RawDataModel) getIntent().getSerializableExtra("obj");
            mRecyclerView.setAdapter(new ActivitiesRVAdapter(RawData.setWaterActivity(), ActivitiesList.getInstance()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void activityClicked(int pos) {
        Toast.makeText(getApplicationContext(), "" + RawData.setWaterActivity().get(pos).getActivityName() + " is selected.", Toast.LENGTH_LONG).show();
    }
}
