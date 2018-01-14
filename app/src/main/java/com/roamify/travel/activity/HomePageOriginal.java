package com.roamify.travel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
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
import com.roamify.travel.adapters.MenuGridRVAdapter;
import com.roamify.travel.listeners.ActivityItemClickListener;
import com.roamify.travel.models.MenuItemModel;
import com.roamify.travel.utils.AppController;
import com.roamify.travel.utils.Constants;
import com.roamify.travel.utils.Validations;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomePageOriginal extends AppCompatActivity implements ActivityItemClickListener, View.OnClickListener {

    static HomePageOriginal mInstance;
    protected TextView autoCompleteTextView;
    FrameLayout top_image_portion;
    LinearLayout rv_list_portion;

    public static synchronized HomePageOriginal getInstance() {
        return mInstance;
    }

    int listViewHeight;
    int totalHeight;
    RecyclerView mMenuListRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.home_page_layout);

        mMenuListRecyclerView = (RecyclerView) findViewById(R.id.rv_menu);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mMenuListRecyclerView.setLayoutManager(mLayoutManager);
        mMenuListRecyclerView.setHasFixedSize(true);
        mMenuListRecyclerView.setItemAnimator(new DefaultItemAnimator());

        top_image_portion = (FrameLayout) findViewById(R.id.ll_top_image_portion);
        rv_list_portion = (LinearLayout) findViewById(R.id.ll_activity_rowLayout);

        try {
            Display mDisplay = getWindowManager().getDefaultDisplay();
            listViewHeight = mDisplay.getHeight() - (mDisplay.getHeight() / 3);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Constants.activityItemClickListener = HomePageOriginal.this;
        try {
            mMenuListRecyclerView.setAdapter(new MenuGridRVAdapter(setMenuData(), HomePageOriginal.getInstance(), listViewHeight / 4));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClicked(String id, String name) {}

    private ArrayList<MenuItemModel> setMenuData() {
        ArrayList<MenuItemModel> menuItemModels = new ArrayList<>();

        //Item1
       /* MenuItemModel title1 = new MenuItemModel();
        title1.setTitle(getResources().getString(R.string.hotels));
        title1.setDrawable(R.drawable.hotels_button_bg);
        menuItemModels.add(title1);*/

        //Item2
        MenuItemModel title2 = new MenuItemModel();
        title2.setTitle(getResources().getString(R.string.water_activity));
        title2.setDrawable(R.drawable.water_activity_button_bg);
        menuItemModels.add(title2);

        //Item3
        MenuItemModel title3 = new MenuItemModel();
        title3.setTitle(getResources().getString(R.string.air_activity));
        title3.setDrawable(R.drawable.air_activity_button_bg);
        menuItemModels.add(title3);

        //Item4
        MenuItemModel title4 = new MenuItemModel();
        title4.setTitle(getResources().getString(R.string.land_activity));
        title4.setDrawable(R.drawable.land_activity_button_bg);
        menuItemModels.add(title4);

        //Item5
        MenuItemModel title5 = new MenuItemModel();
        title5.setTitle(getResources().getString(R.string.destination));
        title5.setDrawable(R.drawable.destination_button_bg);
        menuItemModels.add(title5);

       /* //Item6
        MenuItemModel title6 = new MenuItemModel();
        title6.setTitle(getResources().getString(R.string.contact_us));
        title6.setDrawable(R.drawable.contact_us_button_bg);
        menuItemModels.add(title6);

        //Item7
        MenuItemModel title7 = new MenuItemModel();
        title7.setTitle(getResources().getString(R.string.about_us));
        title7.setDrawable(R.drawable.about_us_button_bg);
        menuItemModels.add(title7);

        //Item8
        MenuItemModel title8 = new MenuItemModel();
        title8.setTitle(getResources().getString(R.string.faq));
        title8.setDrawable(R.drawable.submit_button_bg);
        menuItemModels.add(title8);*/

        return menuItemModels;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.autoCompleteTextView) {
            Validations.hideSoftInput(HomePageOriginal.this);
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            intent.putExtra("title", "Search for?");
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }
    }

    private void initView() {
        autoCompleteTextView = (TextView) findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setOnClickListener(HomePageOriginal.this);
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
