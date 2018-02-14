package com.roamify.travel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.roamify.travel.R;
import com.roamify.travel.adapters.TopDestinationListRVAdapter;
import com.roamify.travel.dialogs.AlertDialogManager;
import com.roamify.travel.models.DestinationModel;
import com.roamify.travel.models.PackageModel;
import com.roamify.travel.utils.AppController;
import com.roamify.travel.utils.CheckConnection;
import com.roamify.travel.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */

public class TopDestinationFragment extends Fragment {
    protected TextView tvTopPopularDestinations;
    protected ImageView ivLeft;
    protected ImageView ivRight;
    protected RelativeLayout rlArrowLayout;
    RecyclerView rvTopActivities;
    private View rootView;
    ArrayList<DestinationModel> arrayList = new ArrayList<>();

    public TopDestinationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_top_destination, container, false);
        initView(rootView);
        try {
            rvTopActivities.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            rvTopActivities.setHasFixedSize(true);
            rvTopActivities.setItemAnimator(new DefaultItemAnimator());

            String URL = Constants.BaseUrl + "getPopularLocation.php";
            if (new CheckConnection(getActivity()).isConnectedToInternet()) {
                try {
                    getRequestCall(URL, "get_top_destination", null);
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
        rvTopActivities = (RecyclerView) rootView.findViewById(R.id.rv_topDestination);
        tvTopPopularDestinations = (TextView) rootView.findViewById(R.id.tv_topPopularDestinations);
        ivLeft = (ImageView) rootView.findViewById(R.id.iv_left);
        ivRight = (ImageView) rootView.findViewById(R.id.iv_right);
        rlArrowLayout = (RelativeLayout) rootView.findViewById(R.id.rl_arrowLayout);
    }

    public void getRequestCall(String url, String tag, JSONObject jsonObject) {
        // cancel request from pending queue
        AppController.getInstance().cancelPendingRequests(tag);

        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
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

    private void runOnMainThread(JSONArray response) throws JSONException {
        //JSONArray jsonArray = new JSONArray(response.toString());
        for (int i = 0; i < response.length(); i++) {
            DestinationModel model = new DestinationModel();
            JSONObject jsonObject = response.getJSONObject(i);
            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            String thumbImage = jsonObject.getString("thumbImage");

            model.setDestinationId(id);
            model.setDestinationName(name);
            model.setDestinationImage(thumbImage);

            arrayList.add(model);
        }
        if (arrayList.size() > 0)
            tvTopPopularDestinations.setVisibility(View.VISIBLE);
            rvTopActivities.setVisibility(View.VISIBLE);
        Collections.sort(arrayList, new Comparator<DestinationModel>() {
            @Override
            public int compare(DestinationModel s1, DestinationModel s2) {
                return s1.getDestinationName().compareToIgnoreCase(s2.getDestinationName());
            }
        });
            rvTopActivities.setAdapter(new TopDestinationListRVAdapter(arrayList, getActivity(), ""));
        if (arrayList.size() > 2) {
            rlArrowLayout.setVisibility(View.VISIBLE);
        }
    }

}
