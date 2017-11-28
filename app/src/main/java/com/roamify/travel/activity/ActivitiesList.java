package com.roamify.travel.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.InflateException;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.roamify.travel.R;
import com.roamify.travel.adapters.DestinationWiseActivityRVAdapter;
import com.roamify.travel.listeners.ActivityItemClickListener;
import com.roamify.travel.models.RawDataModel;
import com.roamify.travel.models.StateWiseActivityModel;
import com.roamify.travel.rawdata.RawData;
import com.roamify.travel.utils.Constants;

import java.util.ArrayList;

public class ActivitiesList extends AppCompatActivity implements ActivityItemClickListener, View.OnClickListener {
    protected Toolbar toolbar;
    protected EditText etSearchLocation;
    protected ImageView imgSearch;
    protected ImageView imgClear;
    protected RecyclerView rvRecyclerView;
    RawDataModel rawDataModel = new RawDataModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_activities_list);

        initView();
        try {
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
            rvRecyclerView.setLayoutManager(mLayoutManager);
            rvRecyclerView.setHasFixedSize(true);
            rvRecyclerView.setItemAnimator(new DefaultItemAnimator());
        } catch (InflateException ie) {
            ie.getMessage();
        }

        etSearchLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    rvRecyclerView.setAdapter(new DestinationWiseActivityRVAdapter(filter(s.toString()), ActivitiesList.this, 0));

                } else {
                    rvRecyclerView.setAdapter(new DestinationWiseActivityRVAdapter(RawData.setStateWiseActivity(), ActivitiesList.this, 0));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    imgClear.setVisibility(View.VISIBLE);
                } else {
                    imgClear.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Constants.activityItemClickListener = ActivitiesList.this;
        try {
            rvRecyclerView.setAdapter(new DestinationWiseActivityRVAdapter(RawData.setStateWiseActivity(), ActivitiesList.this, 0));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClicked(int pos) {
        Intent intent = new Intent(getApplicationContext(), ActivityPackageList.class);
        intent.putExtra("title", RawData.setStateWiseActivity().get(pos).getActivityName());
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgClear) {
            etSearchLocation.setText("");
        }
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        etSearchLocation = (EditText) findViewById(R.id.et_searchNews);
        imgSearch = (ImageView) findViewById(R.id.imgSearch);
        imgClear = (ImageView) findViewById(R.id.imgClear);
        imgClear.setOnClickListener(ActivitiesList.this);
        rvRecyclerView = (RecyclerView) findViewById(R.id.rv_recyclerView);

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

    private ArrayList<StateWiseActivityModel> filter(String folderID) {
        final ArrayList<StateWiseActivityModel> filteredModelList = new ArrayList<>();
        for (int i = 0; i < RawData.setStateWiseActivity().size(); i++) {
            StateWiseActivityModel model = new StateWiseActivityModel();
            final String fId = RawData.setStateWiseActivity().get(i).getActivityName().toLowerCase();
            if (fId.startsWith(folderID.toLowerCase())) {
                model.setActivityId(RawData.setStateWiseActivity().get(i).getActivityId());
                model.setActivityName((RawData.setStateWiseActivity().get(i).getActivityName()));
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
