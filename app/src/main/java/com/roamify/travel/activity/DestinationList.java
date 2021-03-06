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
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.roamify.travel.adapters.DestinationRVAdapter;
import com.roamify.travel.dialogs.AlertDialogManager;
import com.roamify.travel.listeners.ActivityItemClickListener;
import com.roamify.travel.models.DestinationModel;
import com.roamify.travel.utils.AppController;
import com.roamify.travel.utils.CheckConnection;
import com.roamify.travel.utils.Constants;
import com.roamify.travel.utils.Validations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class DestinationList extends AppCompatActivity implements View.OnClickListener, ActivityItemClickListener {

    protected Toolbar toolbar;
    //protected AutoCompleteTextView etSearchDestination;
    protected EditText etSearchDestination;
    protected ImageView imgClear;
    protected RelativeLayout rlSearch;
    protected RecyclerView rvRecyclerView;
    //CustomAutoCompleteView etSearchDestination;
    protected ArrayAdapter<DestinationModel> myAdapter;
    protected String request_tag = "get_destination_by_activity";
    protected ArrayList<DestinationModel> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_destination_list);
        initView();

        findViewById(R.id.right_bar_button).setOnClickListener(this);
        findViewById(R.id.right_bar_search_button).setOnClickListener(this);

        etSearchDestination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    rvRecyclerView.setAdapter(new DestinationRVAdapter(filter(s.toString()), DestinationList.this));
                } else {
                    rvRecyclerView.setAdapter(new DestinationRVAdapter(arrayList, DestinationList.this));
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

        if (getIntent().getBooleanExtra("isComingFromSearchPage", false)) {
            //Display location to specified location
            if (getIntent().getBooleanExtra("isComingFromSearchPageWithState", false)) {
                if (new CheckConnection(getApplicationContext()).isConnectedToInternet()) {
                    String URL = Constants.BaseUrl + "getLocationByState.php?stateId=" + getIntent().getStringExtra("state_id");
                    try {
                        getRequestCall(URL, request_tag);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    AlertDialogManager.showAlartDialog(DestinationList.this, getString(R.string.no_network_title), getString(R.string.no_network_msg));
                }
            } else {
                if (new CheckConnection(getApplicationContext()).isConnectedToInternet()) {
                    String URL = Constants.BaseUrl + "getLocationByActivity.php?activityId=" + getIntent().getStringExtra("id");
                    try {
                        getRequestCall(URL, request_tag);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    AlertDialogManager.showAlartDialog(DestinationList.this, getString(R.string.no_network_title), getString(R.string.no_network_msg));
                }
            }
        } else if (getIntent().getBooleanExtra("isComingForDestinationWiseSearch", false)) {
            //Display location to specified location
            if (new CheckConnection(getApplicationContext()).isConnectedToInternet()) {
                String URL = Constants.BaseUrl + "getAllLocation.php";
                try {
                    getRequestCall(URL, request_tag);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                AlertDialogManager.showAlartDialog(DestinationList.this, getString(R.string.no_network_title), getString(R.string.no_network_msg));
            }
        } else {
            if (new CheckConnection(getApplicationContext()).isConnectedToInternet()) {
                String URL = Constants.BaseUrl + "getLocationByActivityId.php?activityTypeId=" + getIntent().getStringExtra("act_id");
                try {
                    getRequestCall(URL, request_tag);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                AlertDialogManager.showAlartDialog(DestinationList.this, getString(R.string.no_network_title), getString(R.string.no_network_msg));
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            Constants.activityItemClickListener = DestinationList.this;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgClear) {
            etSearchDestination.setText("");
        } else if (view.getId() == R.id.right_bar_button) {
            Intent intent = new Intent(getApplicationContext(), HomePageWithMenu.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
        } else if (view.getId() == R.id.right_bar_search_button) {
            rlSearch.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbarTitle = (TextView)toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(getIntent().getStringExtra("title"));
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
            rvRecyclerView.setNestedScrollingEnabled(false);
        } catch (InflateException ie) {
            ie.getMessage();
        }
        //toolbar.setTitle(getIntent().getStringExtra("title"));
        toolbar.setTitleTextAppearance(this, R.style.NavBarTitle);
        toolbar.setSubtitleTextAppearance(this, R.style.NavBarSubTitle);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } catch (NullPointerException npe) {
            npe.getMessage();
        }

        Validations.centerToolbarTitle(toolbar);

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
                onBackPressed();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validations.hideSoftInput(DestinationList.this);
            }
        });
    }

    @Override
    public void onClicked(String id, String name) {
        Intent intent;
        Validations.hideSoftInput(DestinationList.this);
        try {
            if (getIntent().getBooleanExtra("isComingFromSearchPage", false)) {
                //User come here on select activity from search page, then he should go for package list on select any location
                if (getIntent().getBooleanExtra("isComingFromSearchPageWithState", false)) {
                    try {
                        intent = new Intent(getApplicationContext(), AllActivities.class);
                        intent.putExtra("title", name);
                        intent.putExtra("loc_name", name);
                        intent.putExtra("act_name", getIntent().getStringExtra("title"));
                        intent.putExtra("loc_id", id);
                        intent.putExtra("act_id", getIntent().getStringExtra("id"));
                        startActivity(intent);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    intent = new Intent(getApplicationContext(), ActivityPackageList.class);
                    intent.putExtra("title", name);
                    intent.putExtra("loc_name", name);
                    intent.putExtra("act_name", getIntent().getStringExtra("title"));
                    intent.putExtra("loc_id", id);
                    intent.putExtra("act_id", getIntent().getStringExtra("id"));
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
            } else if (getIntent().getBooleanExtra("isComingForDestinationWiseSearch", false)) {
                //User come here on tap destination tab from home page, then he should go for all activities list on select any location
                intent = new Intent(getApplicationContext(), AllActivities.class);
                intent.putExtra("title", name);
                intent.putExtra("loc_name", name);
                intent.putExtra("loc_id", id);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } else {
                //User come here on tap activity tab from Home page, then he should go for that type activities list on select any location
                intent = new Intent(getApplicationContext(), ActivityPackageList.class);
                intent.putExtra("title", name);
                intent.putExtra("loc_name", name);
                intent.putExtra("loc_id", id);
                intent.putExtra("act_name", getIntent().getStringExtra("act_name"));
                intent.putExtra("act_id", getIntent().getStringExtra("act_id"));
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private ArrayList<DestinationModel> filter(String folderID) {
        final ArrayList<DestinationModel> filteredModelList = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            DestinationModel model = new DestinationModel();
            final String fId = arrayList.get(i).getDestinationName().toLowerCase();
            if (fId.startsWith(folderID.toLowerCase())) {
                model.setDestinationId(arrayList.get(i).getDestinationId());
                model.setDestinationName(arrayList.get(i).getDestinationName());
                model.setDestinationImage(arrayList.get(i).getDestinationImage());
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
        RetryPolicy policy = new DefaultRetryPolicy(Constants.SOCKET_TIME_OUT, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjReq.setRetryPolicy(policy);

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag);
    }

    private void runOnMainThread(JSONObject response) throws JSONException {
        JSONArray jsonArray = response.getJSONArray("details");
        for (int i = 0; i < jsonArray.length(); i++) {
            DestinationModel model = new DestinationModel();
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            String thumbImage = jsonObject.getString("thumbImage");

            model.setDestinationId(id);
            model.setDestinationName(name);
            model.setDestinationImage(thumbImage);

            arrayList.add(model);
        }
        if (arrayList.size() > 0) {
            Collections.sort(arrayList, new Comparator<DestinationModel>() {
                @Override
                public int compare(DestinationModel s1, DestinationModel s2) {
                    return s1.getDestinationName().compareToIgnoreCase(s2.getDestinationName());
                }
            });
            rvRecyclerView.setAdapter(new DestinationRVAdapter(arrayList, DestinationList.this));
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        Constants.activityItemClickListener = null;
    }
}
