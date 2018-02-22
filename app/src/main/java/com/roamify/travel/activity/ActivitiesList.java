package com.roamify.travel.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
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
import com.roamify.travel.adapters.ActivityWiseActivityRVAdapter;
import com.roamify.travel.dialogs.AlertDialogManager;
import com.roamify.travel.listeners.ActivityItemClickListener;
import com.roamify.travel.models.ActivityModel;
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

public class ActivitiesList extends AppCompatActivity implements ActivityItemClickListener, View.OnClickListener {
    protected Toolbar toolbar;
    protected EditText etSearchLocation;
    protected ImageView imgSearch;
    protected ImageView imgClear;
    protected RecyclerView rvRecyclerView;
    protected ImageView rightBarButton;
    protected AppBarLayout appbar;
    protected RelativeLayout rlSearch;
    protected ArrayList<ActivityModel> arrayList = new ArrayList<>();
    protected String request_tag = "get_activity_by_location";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_activities_list);
        initView();
        findViewById(R.id.right_bar_button).setOnClickListener(this);
        findViewById(R.id.right_bar_search_button).setOnClickListener(this);

        try {
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
            rvRecyclerView.setLayoutManager(mLayoutManager);
            rvRecyclerView.setHasFixedSize(true);
            rvRecyclerView.setItemAnimator(new DefaultItemAnimator());
            rvRecyclerView.setNestedScrollingEnabled(false);
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
                    rvRecyclerView.setAdapter(new ActivityWiseActivityRVAdapter(filter(s.toString()), ActivitiesList.this, 0));
                } else {
                    rvRecyclerView.setAdapter(new ActivityWiseActivityRVAdapter(arrayList, ActivitiesList.this, 0));
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

        if (new CheckConnection(getApplicationContext()).isConnectedToInternet()) {
            try {
                String URL = Constants.BaseUrl + "getActivityByType.php?type=" + getIntent().getStringExtra("type");
                getRequestCall(URL, request_tag);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            AlertDialogManager.showAlartDialog(ActivitiesList.this, getString(R.string.no_network_title), getString(R.string.no_network_msg));
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Constants.activityItemClickListener = ActivitiesList.this;
    }

    @Override
    public void onClicked(String id, String name) {
        Intent intent = new Intent(getApplicationContext(), DestinationList.class);
        intent.putExtra("title", name);
        intent.putExtra("act_name", name);
        intent.putExtra("act_id", id);
        intent.putExtra("isComingFromActivities", true);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgClear) {
            etSearchLocation.setText("");
        } else if (view.getId() == R.id.right_bar_button) {
            Intent intent = new Intent(getApplicationContext(), HomePageWithMenu.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
        }else if (view.getId() == R.id.right_bar_search_button) {
            rlSearch.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbarTitle = (TextView)toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(getIntent().getStringExtra("title"));

        etSearchLocation = (EditText) findViewById(R.id.et_searchNews);
        imgSearch = (ImageView) findViewById(R.id.imgSearch);
        imgClear = (ImageView) findViewById(R.id.imgClear);
        imgClear.setOnClickListener(ActivitiesList.this);
        rvRecyclerView = (RecyclerView) findViewById(R.id.rv_recyclerView);

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
                onBackPressed();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validations.hideSoftInput(ActivitiesList.this);
            }
        });

        rightBarButton = (ImageView) findViewById(R.id.right_bar_button);
        rightBarButton.setOnClickListener(ActivitiesList.this);
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        rlSearch = (RelativeLayout) findViewById(R.id.rlSearch);
    }

    private ArrayList<ActivityModel> filter(String folderID) {
        final ArrayList<ActivityModel> filteredModelList = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            ActivityModel model = new ActivityModel();
            final String fId = arrayList.get(i).getActivityName().toLowerCase();
            if (fId.startsWith(folderID.toLowerCase())) {
                model.setActivityId(arrayList.get(i).getActivityId());
                model.setActivityName((arrayList.get(i).getActivityName()));
                model.setPosition((arrayList.get(i).getPosition()));
                model.setActivityIcon((arrayList.get(i).getActivityIcon()));
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
        JSONArray jsonArray = response.getJSONArray("details");
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
            arrayList.add(model);
        }

        if (arrayList.size() > 0) {
            Collections.sort(arrayList, new Comparator<ActivityModel>() {
                @Override
                public int compare(ActivityModel s1, ActivityModel s2) {
                    return s1.getActivityName().compareToIgnoreCase(s2.getActivityName());
                }
            });
            rvRecyclerView.setAdapter(new ActivityWiseActivityRVAdapter(arrayList, ActivitiesList.this, 0));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Constants.activityItemClickListener = null;
    }
}