package com.roamify.travel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.roamify.travel.R;
import com.roamify.travel.activity.ActivityPackageList;
import com.roamify.travel.adapters.ActivitiesPackageListRVAdapter;
import com.roamify.travel.adapters.ReviewsRVAdapter;
import com.roamify.travel.adapters.TopPackageListRVAdapter;
import com.roamify.travel.dialogs.AlertDialogManager;
import com.roamify.travel.models.PackageModel;
import com.roamify.travel.models.RawDataModel;
import com.roamify.travel.models.ReviewModel;
import com.roamify.travel.rawdata.RawData;
import com.roamify.travel.utils.AppController;
import com.roamify.travel.utils.CheckConnection;
import com.roamify.travel.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopActivitiesFragment extends Fragment {
    RecyclerView rvTopActivities;
    private View rootView;
    ArrayList<PackageModel> arrayList = new ArrayList<>();
    public TopActivitiesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_top_activities, container, false);
        initView(rootView);
        try {
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
            //rvTopActivities.setLayoutManager(mLayoutManager);
            rvTopActivities.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

            rvTopActivities.setHasFixedSize(true);
            rvTopActivities.setItemAnimator(new DefaultItemAnimator());

            String URL = Constants.BaseUrl + "getPackageListByActivity.php?activityId=5a28cf8012da5";
            if (new CheckConnection(getActivity()).isConnectedToInternet()) {
                try {
                    getRequestCall(URL, "get_top_activity", null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                AlertDialogManager.showAlartDialog(getActivity(), getString(R.string.no_network_title), getString(R.string.no_network_msg));
            }

        } catch (InflateException ie) {
            ie.getMessage();
        }
        return rootView;
    }
    private void initView(View rootView) {
        rvTopActivities = (RecyclerView) rootView.findViewById(R.id.rv_topActivities);
    }

    public void getRequestCall(String url, String tag, JSONObject jsonObject) {
        // cancel request from pending queue
        AppController.getInstance().cancelPendingRequests(tag);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, jsonObject,
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
        rvTopActivities.setAdapter(new TopPackageListRVAdapter(arrayList, getActivity(),""));

    }
}
