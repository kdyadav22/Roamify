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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.InflateException;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.roamify.travel.R;
import com.roamify.travel.adapters.DestinationRVAdapter;
import com.roamify.travel.adapters.SearchRVAdapter;
import com.roamify.travel.listeners.ActivityItemClickListener;
import com.roamify.travel.models.ActivityModel;
import com.roamify.travel.rawdata.RawData;
import com.roamify.travel.utils.Constants;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, ActivityItemClickListener {

    protected Toolbar toolbar;
    protected EditText etSearch;
    protected RecyclerView rvRecyclerView;
    protected ImageView imgClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_search);
        initView();
        Constants.activityItemClickListener = this;
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    rvRecyclerView.setAdapter(new SearchRVAdapter(filter(s.toString()), SearchActivity.this));
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

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        etSearch = (EditText) findViewById(R.id.et_search);
        rvRecyclerView = (RecyclerView) findViewById(R.id.rv_recyclerView);
        try {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rvRecyclerView.setLayoutManager(mLayoutManager);
            rvRecyclerView.setHasFixedSize(true);
            rvRecyclerView.setItemAnimator(new DefaultItemAnimator());
        } catch (InflateException ie) {
            ie.getMessage();
        }
        toolbar.setTitle("");
        toolbar.setTitleTextAppearance(this, R.style.NavBarTitle);
        toolbar.setSubtitleTextAppearance(this, R.style.NavBarSubTitle);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException npe) {
            npe.getMessage();
        }

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });
        imgClear = (ImageView) findViewById(R.id.imgClear);
        imgClear.setOnClickListener(SearchActivity.this);
    }

    private ArrayList<ActivityModel> filter(String folderID) {
        final ArrayList<ActivityModel> filteredModelList = new ArrayList<>();
        for (int i = 0; i < RawData.setDestination().size(); i++) {
            ActivityModel model = new ActivityModel();
            final String fId = RawData.setDestination().get(i).getActivityName().toLowerCase();
            if (fId.contains(folderID.toLowerCase())) {
                model.setActivityId(RawData.setDestination().get(i).getActivityId());
                model.setActivityName((RawData.setDestination().get(i).getActivityName()));
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgClear) {
            etSearch.setText("");
        }
    }

    @Override
    public void onClicked(int pos) {
        try {

            //If item is location type then will go on "All Activites Page"

            //If item is activity type then will go on "Destination List Page"

            //Below redirection just for testing purpose
            Intent intent = new Intent(getApplicationContext(), AllActivities.class);
            intent.putExtra("title", RawData.setDestination().get(pos).getActivityName());
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
