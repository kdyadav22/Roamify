package com.roamify.travel.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.InflateException;
import android.widget.TextView;
import android.widget.Toast;

import com.roamify.travel.R;
import com.roamify.travel.adapters.ActivitiesRVAdapter;
import com.roamify.travel.listeners.ActivityItemClickListener;
import com.roamify.travel.models.RawDataModel;
import com.roamify.travel.rawdata.RawData;

public class ActivitiesList extends AppCompatActivity implements ActivityItemClickListener {

    public static ActivityItemClickListener activityItemClickListener;
    static ActivitiesList mInstance;
    TextView headerTextView;
    RawDataModel rawDataModel = new RawDataModel();

    public static synchronized ActivitiesList getInstance() {
        return mInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_list);
        activityItemClickListener = this;
        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_recyclerView);
        headerTextView = (TextView) findViewById(R.id.tv_headerTitle);
        try {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        } catch (InflateException ie) {
            ie.getMessage();
        }

        try {
            if (getIntent().hasExtra("title")) {
                headerTextView.setText(getIntent().getStringExtra("title"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
            /*Bundle bundle = getIntent().getExtras();
            ObjectA obj = bundle.getParcelable("obj");

            Log.i("---------- Id   ",":: "+obj.getIntValue());
            Log.i("---------- Name ",":: "+obj.getStrValue());*/

                    switch (getIntent().hasExtra("title") ? getIntent().getStringExtra("title") : "") {
                        case "WATER ACTIVITIES": {
                            rawDataModel.setActivityModelarrayList(RawData.setWaterActivity());
                            break;
                        }
                        case "AIR ACTIVITIES": {
                            rawDataModel.setActivityModelarrayList(RawData.setAirActivity());
                            break;
                        }
                        case "LAND ACTIVITIES": {
                            rawDataModel.setActivityModelarrayList(RawData.setLandActivity());
                            break;
                        }
                        case "DESTINATIONS": {
                            rawDataModel.setActivityModelarrayList(RawData.setDestination());
                            break;
                        }
                    }
                    //ArrayList<ActivityModel> rawDataModel = (ArrayList<ActivityModel>) getIntent().getSerializableExtra("obj");
                    mRecyclerView.setAdapter(new ActivitiesRVAdapter(rawDataModel.getActivityModelarrayList(), ActivitiesList.getInstance()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void activityClicked(int pos) {
        Toast.makeText(getApplicationContext(), "" + RawData.setWaterActivity().get(pos).getActivityName() + " is selected.", Toast.LENGTH_LONG).show();
    }
}
