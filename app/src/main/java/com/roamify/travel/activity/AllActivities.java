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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.roamify.travel.R;
import com.roamify.travel.adapters.AutocompleteAllActivityAdapter;
import com.roamify.travel.adapters.CustomAutoCompleteView;
import com.roamify.travel.adapters.DestinationWiseActivityRVAdapter;
import com.roamify.travel.listeners.ActivityItemClickListener;
import com.roamify.travel.models.StateWiseActivityModel;
import com.roamify.travel.rawdata.RawData;
import com.roamify.travel.utils.AppController;
import com.roamify.travel.utils.Constants;
import com.roamify.travel.utils.Validations;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllActivities extends AppCompatActivity implements ActivityItemClickListener {

    protected Toolbar toolbar;
    protected CustomAutoCompleteView etSearchDestination;
    ArrayAdapter<StateWiseActivityModel> myAdapter;
    protected ImageView imgClear;
    protected RecyclerView rvLandRecyclerView;
    protected RecyclerView rvWaterRecyclerView;
    protected RecyclerView rvAirRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_all_activities);
        initView();

        etSearchDestination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    try {
                        myAdapter.notifyDataSetChanged();
                        myAdapter = new AutocompleteAllActivityAdapter(AllActivities.this, R.layout.autocomplete_allactivity_text_layout, filter(charSequence.toString()));
                        etSearchDestination.setAdapter(myAdapter);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etSearchDestination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                try {
                    Validations.hideSoftInput(AllActivities.this);

                    LinearLayout rl = (LinearLayout) arg1;
                    LinearLayout rl1 = (LinearLayout) rl.getChildAt(0);
                    LinearLayout rl2 = (LinearLayout) rl1.getChildAt(1);
                    TextView actNameTextView = (TextView) rl2.getChildAt(0);
                    TextView actIdTextView = (TextView) rl2.getChildAt(1);
                    etSearchDestination.setText(actNameTextView.getText().toString());

                    Intent intent;
                    intent = new Intent(getApplicationContext(), ActivityPackageList.class);
                    intent.putExtra("title", actNameTextView.getText().toString());
                    intent.putExtra("id", actIdTextView.getText().toString());

                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        myAdapter = new AutocompleteAllActivityAdapter(AllActivities.this, R.layout.autocomplete_allactivity_text_layout, RawData.setStateWiseActivity());
        etSearchDestination.setAdapter(myAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Constants.activityItemClickListener = AllActivities.this;
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
        etSearchDestination = (CustomAutoCompleteView) findViewById(R.id.et_autosearch);

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
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
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

    public void getRequestCall(String url, String tag) {
        // cancel request from pending queue
        AppController.getInstance().cancelPendingRequests(tag);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());
                        try {
                            runOnMainThread(response);
                        } catch (JSONException ex) {
                            ex.getMessage();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
            }
        })

        {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        //Adding policy for socket time out
        RetryPolicy policy = new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjReq.setRetryPolicy(policy);

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag);
    }

    private void runOnMainThread(JSONObject response) throws JSONException {

    }

}
