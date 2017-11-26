package com.roamify.travel.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.roamify.travel.R;
import com.roamify.travel.adapters.DestinationWiseActivityRVAdapter;
import com.roamify.travel.listeners.ActivityItemClickListener;
import com.roamify.travel.rawdata.RawData;
import com.roamify.travel.utils.Constants;

public class AllActivities extends AppCompatActivity implements ActivityItemClickListener{

    protected Toolbar toolbar;
    protected RecyclerView rvLandRecyclerView;
    protected RecyclerView rvWaterRecyclerView;
    protected RecyclerView rvAirRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_all_activities);
        initView();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Constants.activityItemClickListener=AllActivities.this;
        try {
            rvLandRecyclerView.setAdapter(new DestinationWiseActivityRVAdapter(RawData.setStateWiseActivity(), AllActivities.this, 0));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            rvWaterRecyclerView.setAdapter(new DestinationWiseActivityRVAdapter(RawData.setStateWiseActivity(), AllActivities.this, 0));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            rvAirRecyclerView.setAdapter(new DestinationWiseActivityRVAdapter(RawData.setStateWiseActivity(), AllActivities.this, 0));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rvLandRecyclerView = (RecyclerView) findViewById(R.id.rv_LandRecyclerView);
        rvLandRecyclerView.setLayoutManager(new LinearLayoutManager(AllActivities.this, LinearLayoutManager.HORIZONTAL, false));
        rvLandRecyclerView.setHasFixedSize(true);
        rvLandRecyclerView.setItemAnimator(new DefaultItemAnimator());

        rvWaterRecyclerView = (RecyclerView) findViewById(R.id.rv_WaterRecyclerView);
        rvWaterRecyclerView.setLayoutManager(new LinearLayoutManager(AllActivities.this, LinearLayoutManager.HORIZONTAL, false));
        rvWaterRecyclerView.setHasFixedSize(true);
        rvWaterRecyclerView.setItemAnimator(new DefaultItemAnimator());

        rvAirRecyclerView = (RecyclerView) findViewById(R.id.rv_AirRecyclerView);
        rvAirRecyclerView.setLayoutManager(new LinearLayoutManager(AllActivities.this, LinearLayoutManager.HORIZONTAL, false));
        rvAirRecyclerView.setHasFixedSize(true);
        rvAirRecyclerView.setItemAnimator(new DefaultItemAnimator());

        toolbar.setTitle(getIntent().getStringExtra("title"));
        toolbar.setTitleTextAppearance(this, R.style.NavBarTitle);
        toolbar.setSubtitleTextAppearance(this, R.style.NavBarSubTitle);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException npe) {
            npe.getMessage();
        }

        try {
            final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
            upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }
        catch (RuntimeException re)
        {
            re.printStackTrace();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });
    }

    @Override
    public void onClicked(int pos) {
        Intent intent;
        try {
            intent = new Intent(getApplicationContext(), ActivityPackageList.class);
            intent.putExtra("title", RawData.setStateWiseActivity().get(pos).getActivityName());
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
