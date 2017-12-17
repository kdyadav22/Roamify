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
import com.roamify.travel.dialogs.AlertDialogManager;
import com.roamify.travel.listeners.AllActivityItemClickListener;
import com.roamify.travel.models.ActivityModel;
import com.roamify.travel.utils.AppController;
import com.roamify.travel.utils.CheckConnection;
import com.roamify.travel.utils.Constants;
import com.roamify.travel.utils.Validations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllActivities extends AppCompatActivity implements AllActivityItemClickListener, View.OnClickListener {

    protected Toolbar toolbar;
    protected CustomAutoCompleteView etSearchDestination;
    protected LinearLayout llLandActivities;
    protected LinearLayout llWaterActivities;
    protected LinearLayout llAirActivities;
    ArrayAdapter<ActivityModel> myAdapter;
    protected ImageView imgClear;
    protected RecyclerView rvLandRecyclerView;
    protected RecyclerView rvWaterRecyclerView;
    protected RecyclerView rvAirRecyclerView;
    String request_tag = "get_all_activity";
    ArrayList<ActivityModel> arrayList = new ArrayList<>();
    ArrayList<ActivityModel> arrayList1 = new ArrayList<>();
    ArrayList<ActivityModel> arrayList2 = new ArrayList<>();
    ArrayList<ActivityModel> arrayList3 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_all_activities);
        initView();
        findViewById(R.id.right_bar_button).setOnClickListener(this);
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

        if (new CheckConnection(getApplicationContext()).isConnectedToInternet()) {
            String URL = Constants.BaseUrl + "getAllActivityByLocation.php?locationId=" + getIntent().getStringExtra("loc_id");
            try {
                getRequestCall(URL, request_tag);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            AlertDialogManager.showAlartDialog(AllActivities.this, getString(R.string.no_network_title), getString(R.string.no_network_msg));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Constants.allActivityItemClickListener = AllActivities.this;
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

        toolbar.setTitle("Activities");
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

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validations.hideSoftInput(AllActivities.this);
            }
        });
        imgClear = (ImageView) findViewById(R.id.imgClear);
        llLandActivities = (LinearLayout) findViewById(R.id.ll_landActivities);
        llWaterActivities = (LinearLayout) findViewById(R.id.ll_waterActivities);
        llAirActivities = (LinearLayout) findViewById(R.id.ll_airActivities);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.right_bar_button) {
            Intent intent = new Intent(getApplicationContext(), HomePageWithMenu.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
        }
    }

    @Override
    public void onClicked(String pos) {
        Intent intent;
        try {
            intent = new Intent(getApplicationContext(), ActivityPackageList.class);
            intent.putExtra("loc_name", getIntent().getStringExtra("loc_name"));
            //intent.putExtra("act_name", arrayList.get(pos).getActivityName());
            intent.putExtra("loc_id", getIntent().getStringExtra("loc_id"));
            intent.putExtra("act_id", pos);
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private ArrayList<ActivityModel> filter(String folderID) {
        final ArrayList<ActivityModel> filteredModelList = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            ActivityModel model = new ActivityModel();
            final String fId = arrayList.get(i).getActivityName().toLowerCase();
            if (fId.startsWith(folderID.toLowerCase())) {
                model.setActivityId(arrayList.get(i).getActivityId());
                model.setActivityName((arrayList.get(i).getActivityName()));
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
        int objLen = response.length();
        Log.d("ObjLen", "ObjLen" + objLen);

        JSONArray jsonArray = response.getJSONArray("Water");
        if (jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                ActivityModel model = new ActivityModel();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String thumbImage = jsonObject.getString("thumbImage");

                model.setActivityId(id);
                model.setActivityName(name);
                model.setActivityIcon(thumbImage);
                model.setPosition(i);
                arrayList1.add(model);
                arrayList.add(model);
            }

            try {
                llWaterActivities.setVisibility(View.VISIBLE);
                rvWaterRecyclerView.setAdapter(new DestinationWiseActivityRVAdapter(arrayList1, AllActivities.this, 0));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


        JSONArray jsonArray1 = response.getJSONArray("Land");
        if (jsonArray1.length() > 0) {
            for (int i = 0; i < jsonArray1.length(); i++) {
                ActivityModel model = new ActivityModel();
                JSONObject jsonObject = jsonArray1.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String thumbImage = jsonObject.getString("thumbImage");

                model.setActivityId(id);
                model.setActivityName(name);
                model.setActivityIcon(thumbImage);
                model.setPosition(i);
                arrayList2.add(model);
                arrayList.add(model);
            }
            try {
                llLandActivities.setVisibility(View.VISIBLE);
                rvLandRecyclerView.setAdapter(new DestinationWiseActivityRVAdapter(arrayList2, AllActivities.this, 0));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (jsonArray1.length() > 0) {
            JSONArray jsonArray2 = response.getJSONArray("Air");
            for (int i = 0; i < jsonArray2.length(); i++) {
                ActivityModel model = new ActivityModel();
                JSONObject jsonObject = jsonArray2.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String thumbImage = jsonObject.getString("thumbImage");

                model.setActivityId(id);
                model.setActivityName(name);
                model.setActivityIcon(thumbImage);
                model.setPosition(i);
                arrayList3.add(model);
                arrayList.add(model);
            }

            try {
                llAirActivities.setVisibility(View.VISIBLE);
                rvAirRecyclerView.setAdapter(new DestinationWiseActivityRVAdapter(arrayList3, AllActivities.this, 0));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        myAdapter = new AutocompleteAllActivityAdapter(AllActivities.this, R.layout.autocomplete_allactivity_text_layout, arrayList);
        etSearchDestination.setAdapter(myAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Constants.activityItemClickListener = null;
        Constants.allActivityItemClickListener = null;
    }
}
