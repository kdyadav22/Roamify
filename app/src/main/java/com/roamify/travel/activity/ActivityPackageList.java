package com.roamify.travel.activity;

import android.app.Activity;
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
import android.view.InflateException;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.roamify.travel.R;
import com.roamify.travel.adapters.ActivitiesPackageListRVAdapter;
import com.roamify.travel.dialogs.AlertDialogManager;
import com.roamify.travel.models.ActivityModel;
import com.roamify.travel.models.PackageModel;
import com.roamify.travel.rawdata.RawData;
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

public class ActivityPackageList extends AppCompatActivity implements View.OnClickListener {
    Activity currentActivity = null;
    RecyclerView recyclerView;
    protected EditText etSearchDestination;
    protected ImageView imgClear;
    ArrayList<PackageModel> arrayList = new ArrayList<>();
    String request_tag = "get_package_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_list);
        findViewById(R.id.right_bar_button).setOnClickListener(this);
        etSearchDestination = (EditText) findViewById(R.id.et_searchNews);
        imgClear = (ImageView) findViewById(R.id.imgClear);
        imgClear.setOnClickListener(ActivityPackageList.this);
        recyclerView = (RecyclerView) findViewById(R.id.rv_recyclerView);
        try {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        } catch (InflateException ie) {
            ie.getMessage();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Package List");
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
                onBackPressed();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validations.hideSoftInput(ActivityPackageList.this);
            }
        });

        //listView = (ListView) findViewById(R.id.navList);

        //listCellAdapter = new ListCellAdapter();
        //listView.setOnItemClickListener(didSelectedListCell);
        //setAdapter();
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        /*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();*/
        etSearchDestination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
//                    myAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(new ActivitiesPackageListRVAdapter(filter(s.toString()), ActivityPackageList.this, ""));
                } else {
                    recyclerView.setAdapter(new ActivitiesPackageListRVAdapter(arrayList, ActivityPackageList.this, ""));
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
            if (getIntent().getBooleanExtra("isComingFromSearchPage", false)) {
                String URL = Constants.BaseUrl + "getEquivalentPackage.php?packageId=" + getIntent().getStringExtra("id");
                try {
                    getRequestCall(URL, request_tag);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                String URL = Constants.BaseUrl + "getPackageListByActivity.php?activityId=" + getIntent().getStringExtra("act_id") + "&locationId=" + getIntent().getStringExtra("loc_id");
                try {
                    getRequestCall(URL, request_tag);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            AlertDialogManager.showAlartDialog(ActivityPackageList.this, getString(R.string.no_network_title), getString(R.string.no_network_msg));
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        currentActivity = ActivityPackageList.this;
    }

    @Override
    public void onBackPressed() {
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
        super.onBackPressed();
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

    private ArrayList<PackageModel> filter(String folderID) {
        final ArrayList<PackageModel> filteredModelList = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            PackageModel model = new PackageModel();
            final String fId = arrayList.get(i).getPackageName().toLowerCase();
            if (fId.startsWith(folderID.toLowerCase())) {
                model.setPackageId(arrayList.get(i).getPackageId());
                model.setPackageName(arrayList.get(i).getPackageName());
                model.setPackageImageName(arrayList.get(i).getPackageImageName());
                model.setPackageDuration(arrayList.get(i).getPackageDuration());
                model.setPackagePrice(arrayList.get(i).getPackagePrice());
                model.setPackageReview(arrayList.get(i).getPackageReview());
                model.setPackageSource(arrayList.get(i).getPackageSource());
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
            PackageModel model = new PackageModel();
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            String review = jsonObject.getString("review");
            String duration = jsonObject.getString("duration");
            String price = jsonObject.getString("price");
            String source = jsonObject.getString("source");
            String thumbImage = jsonObject.getString("thumbImage");
            //String locationId = jsonObject.getString("locationId");
            model.setPackageId(id);
            model.setPackageName(name);
            model.setPackageReview(review);
            model.setPackageDuration(duration);
            model.setPackagePrice(price);
            model.setPackageSource(source);
            model.setPackageImageName(thumbImage);
            arrayList.add(model);
        }

        if (arrayList.size() > 0)
            recyclerView.setAdapter(new ActivitiesPackageListRVAdapter(arrayList, ActivityPackageList.this, ""));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Constants.activityItemClickListener = null;
    }
}
