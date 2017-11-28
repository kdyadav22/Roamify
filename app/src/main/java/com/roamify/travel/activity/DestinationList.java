package com.roamify.travel.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.InflateException;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.roamify.travel.R;
import com.roamify.travel.adapters.AutocompleteCustomArrayAdapter;
import com.roamify.travel.adapters.CustomAutoCompleteView;
import com.roamify.travel.adapters.DestinationRVAdapter;
import com.roamify.travel.listeners.ActivityItemClickListener;
import com.roamify.travel.models.ActivityModel;
import com.roamify.travel.rawdata.RawData;
import com.roamify.travel.utils.Constants;
import com.roamify.travel.utils.Validations;

import java.util.ArrayList;
import java.util.HashMap;

public class DestinationList extends AppCompatActivity implements View.OnClickListener, ActivityItemClickListener {

    protected Toolbar toolbar;
    //protected EditText etSearchDestination;
    protected ImageView imgClear;
    protected RelativeLayout rlSearch;
    protected RecyclerView rvRecyclerView;
    CustomAutoCompleteView etSearchDestination;
    ArrayAdapter<ActivityModel> myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_destination_list);
        initView();

        etSearchDestination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    myAdapter.notifyDataSetChanged();
                    etSearchDestination.setAdapter(new AutocompleteCustomArrayAdapter(DestinationList.this, R.layout.destination_list_item, filter(s.toString())));
                } /*else {
                    rvRecyclerView.setAdapter(new DestinationRVAdapter(RawData.setDestination(), DestinationList.this));
                }*/
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

        etSearchDestination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                RelativeLayout rl = (RelativeLayout) arg1;
                LinearLayout rl1 = (LinearLayout) rl.getChildAt(0);
                TextView tv = (TextView) rl1.getChildAt(0);
                etSearchDestination.setText(tv.getText().toString());
                Intent intent;
                Validations.hideSoftInput(DestinationList.this);
                try {
                    if (getIntent().getBooleanExtra("isComingFromSearchPage", false)) {
                        //User come here on select activity from search page, then he should go for package list on select any location
                        intent = new Intent(getApplicationContext(), ActivityPackageList.class);
                        intent.putExtra("title", tv.getText().toString());
                        startActivity(intent);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    } else if (getIntent().getBooleanExtra("isComingForDestinationWiseSearch", false)) {
                        //User come here on tap destination tab from home page, then he should go for all activities list on select any location
                        intent = new Intent(getApplicationContext(), AllActivities.class);
                        intent.putExtra("title", tv.getText().toString());
                        startActivity(intent);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    } else {
                        //User come here on tap activity tab from Home page, then he should go for that type activities list on select any location
                        intent = new Intent(getApplicationContext(), ActivitiesList.class);
                        intent.putExtra("title", tv.getText().toString());
                        startActivity(intent);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

        myAdapter = new AutocompleteCustomArrayAdapter(DestinationList.this, R.layout.destination_list_item, RawData.setDestination());
        etSearchDestination.setAdapter(myAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            Constants.activityItemClickListener = DestinationList.this;
            rvRecyclerView.setAdapter(new DestinationRVAdapter(RawData.setDestination(), this));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgClear) {
            etSearchDestination.setText("");
        }
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        etSearchDestination = (CustomAutoCompleteView) findViewById(R.id.et_searchNews);
        imgClear = (ImageView) findViewById(R.id.imgClear);
        imgClear.setOnClickListener(DestinationList.this);
        rlSearch = (RelativeLayout) findViewById(R.id.rlSearch);
        rvRecyclerView = (RecyclerView) findViewById(R.id.rv_recyclerView);
        try {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rvRecyclerView.setLayoutManager(mLayoutManager);
            rvRecyclerView.setHasFixedSize(true);
            rvRecyclerView.setItemAnimator(new DefaultItemAnimator());
        } catch (InflateException ie) {
            ie.getMessage();
        }
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
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validations.hideSoftInput(DestinationList.this);
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });
    }

    @Override
    public void onClicked(int pos) {
        Intent intent;
        Validations.hideSoftInput(DestinationList.this);
        try {
            if (getIntent().getBooleanExtra("isComingFromSearchPage", false)) {
                //User come here on select activity from search page, then he should go for package list on select any location
                intent = new Intent(getApplicationContext(), ActivityPackageList.class);
                intent.putExtra("title", RawData.setDestination().get(pos).getActivityName());
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } else if (getIntent().getBooleanExtra("isComingForDestinationWiseSearch", false)) {
                //User come here on tap destination tab from home page, then he should go for all activities list on select any location
                intent = new Intent(getApplicationContext(), AllActivities.class);
                intent.putExtra("title", RawData.setDestination().get(pos).getActivityName());
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } else {
                //User come here on tap activity tab from Home page, then he should go for that type activities list on select any location
                intent = new Intent(getApplicationContext(), ActivitiesList.class);
                intent.putExtra("title", RawData.setDestination().get(pos).getActivityName());
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private ArrayList<ActivityModel> filter(String folderID) {
        final ArrayList<ActivityModel> filteredModelList = new ArrayList<>();
        for (int i = 0; i < RawData.setDestination().size(); i++) {
            ActivityModel model = new ActivityModel();
            final String fId = RawData.setDestination().get(i).getActivityName().toLowerCase();
            if (fId.startsWith(folderID.toLowerCase())) {
                model.setActivityId(RawData.setDestination().get(i).getActivityId());
                model.setActivityName((RawData.setDestination().get(i).getActivityName()));
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

}
