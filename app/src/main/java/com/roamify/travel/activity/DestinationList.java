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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.roamify.travel.R;
import com.roamify.travel.adapters.DestinationRVAdapter;
import com.roamify.travel.listeners.ActivityItemClickListener;
import com.roamify.travel.models.DestinationModel;
import com.roamify.travel.rawdata.RawData;
import com.roamify.travel.utils.Constants;
import com.roamify.travel.utils.Validations;

import java.util.ArrayList;

public class DestinationList extends AppCompatActivity implements View.OnClickListener, ActivityItemClickListener {

    protected Toolbar toolbar;
    //protected AutoCompleteTextView etSearchDestination;
    protected EditText etSearchDestination;
    protected ImageView imgClear;
    protected RelativeLayout rlSearch;
    protected RecyclerView rvRecyclerView;
    //CustomAutoCompleteView etSearchDestination;
    ArrayAdapter<DestinationModel> myAdapter;

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
                    rvRecyclerView.setAdapter(new DestinationRVAdapter(filter(s.toString()), DestinationList.this));
                } else {
                    rvRecyclerView.setAdapter(new DestinationRVAdapter(RawData.setDestination(), DestinationList.this));
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

        /*etSearchDestination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        });*/

        /*myAdapter = new AutocompleteDestinationAdapter(DestinationList.this, R.layout.destination_list_item, RawData.setDestination());
        etSearchDestination.setAdapter(myAdapter);*/
        rvRecyclerView.setAdapter(new DestinationRVAdapter(RawData.setDestination(), DestinationList.this));
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
        etSearchDestination = (EditText) findViewById(R.id.et_searchNews);
        imgClear = (ImageView) findViewById(R.id.imgClear);
        imgClear.setOnClickListener(DestinationList.this);
        rlSearch = (RelativeLayout) findViewById(R.id.rlSearch);
        rvRecyclerView = (RecyclerView) findViewById(R.id.rv_recyclerView);
        try {
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
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
                intent.putExtra("title", RawData.setDestination().get(pos).getDestinationName());
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } else if (getIntent().getBooleanExtra("isComingForDestinationWiseSearch", false)) {
                //User come here on tap destination tab from home page, then he should go for all activities list on select any location
                intent = new Intent(getApplicationContext(), AllActivities.class);
                intent.putExtra("title", RawData.setDestination().get(pos).getDestinationName());
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } else {
                //User come here on tap activity tab from Home page, then he should go for that type activities list on select any location
                intent = new Intent(getApplicationContext(), ActivitiesList.class);
                intent.putExtra("title", RawData.setDestination().get(pos).getDestinationName());
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private ArrayList<DestinationModel> filter(String folderID) {
        final ArrayList<DestinationModel> filteredModelList = new ArrayList<>();
        for (int i = 0; i < RawData.setDestination().size(); i++) {
            DestinationModel model = new DestinationModel();
            final String fId = RawData.setDestination().get(i).getDestinationName().toLowerCase();
            if (fId.startsWith(folderID.toLowerCase())) {
                model.setDestinationId(RawData.setDestination().get(i).getDestinationId());
                model.setDestinationName((RawData.setDestination().get(i).getDestinationName()));
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    private void goToNext()
    {

    }

}
