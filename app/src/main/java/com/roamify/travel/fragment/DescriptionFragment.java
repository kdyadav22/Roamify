package com.roamify.travel.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
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
import com.roamify.travel.activity.ActivityPackageDetails;
import com.roamify.travel.adapters.SourceRVAdapterDetails;
import com.roamify.travel.dialogs.AlertDialogManager;
import com.roamify.travel.listeners.RatingBarCallback;
import com.roamify.travel.models.PackageDetailsModel;
import com.roamify.travel.models.SourceSiteModel;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class DescriptionFragment extends Fragment implements RatingBarCallback {

    float ratingNumber = 0.0f;
    protected View rootView;
    protected TextView tvDetailsDuration;
    protected TextView tvDetailsPrice;
    protected TextView tvDetailsSource;
    protected TextView tvDetailsDesc;
    protected TextView tvDetailsSpec;
    protected LinearLayout llDetailsSpec;
    protected TextView tvDetailsFtr;
    protected LinearLayout llDetailsFtr;
    protected LinearLayout llDetailsDuration;
    protected LinearLayout llDetailsPrice;
    protected LinearLayout llDetailsSource;
    protected RecyclerView rvRecyclerView;
    protected LinearLayout llDescSpec;
    ArrayList<SourceSiteModel> sourceSiteModelArrayList = new ArrayList<>();

    public DescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_description, container, false);
        initView(rootView);
        Constants.ratingBarCallback = this;
        PackageDetailsModel packageDetailsModel = ActivityPackageDetails.getInstance().packageDetailsModel;

        String pacaDuration = packageDetailsModel.getDuration();
        if (Validations.isNotNullNotEmptyNotWhiteSpace(pacaDuration)) {
            llDetailsDuration.setVisibility(View.VISIBLE);
            tvDetailsDuration.setText(pacaDuration);
        }

        String packPrice = packageDetailsModel.getPackagePrice();
        if (Validations.isNotNullNotEmptyNotWhiteSpace(packPrice)) {
            llDetailsPrice.setVisibility(View.VISIBLE);
            tvDetailsPrice.setText("Rs. " + packPrice + " per person");
        }

        JSONArray packSrc = packageDetailsModel.getSource();
        if (packSrc.length() > 0) {
            try {
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                rvRecyclerView.setLayoutManager(mLayoutManager);
                rvRecyclerView.setHasFixedSize(true);
                rvRecyclerView.setItemAnimator(new DefaultItemAnimator());
            } catch (InflateException ie) {
                ie.getMessage();
            }

            for (int i = 0; i < packSrc.length(); i++) {
                try {
                    JSONObject jsonObject = packSrc.getJSONObject(i);
                    SourceSiteModel sourceSiteModel = new SourceSiteModel();
                    sourceSiteModel.setSourceId(jsonObject.getString("id"));
                    sourceSiteModel.setSourceUrl(jsonObject.getString("sourceUrl"));
                    sourceSiteModel.setRating(jsonObject.getString("ratings"));
                    sourceSiteModelArrayList.add(sourceSiteModel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (sourceSiteModelArrayList.size() > 0) {
                rvRecyclerView.setVisibility(View.VISIBLE);
                rvRecyclerView.setAdapter(new SourceRVAdapterDetails(sourceSiteModelArrayList, getActivity()));
                llDetailsSource.setVisibility(View.VISIBLE);
            }
        }

        String packDesc = packageDetailsModel.getDescription();
        if (Validations.isNotNullNotEmptyNotWhiteSpace(packDesc)) {
            llDescSpec.setVisibility(View.VISIBLE);
            tvDetailsDesc.setText(packDesc);
        }

        String packSpeci = packageDetailsModel.getSpecification();
        if (Validations.isNotNullNotEmptyNotWhiteSpace(packSpeci)) {
            llDetailsSpec.setVisibility(View.VISIBLE);
            tvDetailsSpec.setText(packSpeci);
        }

        String packFeature = packageDetailsModel.getFeatures();
        if (Validations.isNotNullNotEmptyNotWhiteSpace(packFeature)) {
            llDetailsFtr.setVisibility(View.VISIBLE);
            tvDetailsFtr.setText(packFeature);
        }

        return rootView;
    }

    private void initView(View rootView) {
        tvDetailsDuration = (TextView) rootView.findViewById(R.id.tv_details_duration);
        tvDetailsPrice = (TextView) rootView.findViewById(R.id.tv_details_price);
        //tvDetailsSource = (TextView) rootView.findViewById(R.id.tv_details_source);
        tvDetailsDesc = (TextView) rootView.findViewById(R.id.tv_details_desc);
        tvDetailsSpec = (TextView) rootView.findViewById(R.id.tv_details_spec);
        llDetailsSpec = (LinearLayout) rootView.findViewById(R.id.ll_details_spec);
        tvDetailsFtr = (TextView) rootView.findViewById(R.id.tv_details_ftr);
        llDetailsFtr = (LinearLayout) rootView.findViewById(R.id.ll_details_ftr);
        llDetailsDuration = (LinearLayout) rootView.findViewById(R.id.ll_details_duration);
        llDetailsPrice = (LinearLayout) rootView.findViewById(R.id.ll_details_price);
        llDetailsSource = (LinearLayout) rootView.findViewById(R.id.ll_details_source);
        rvRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_recyclerView);
        llDescSpec = (LinearLayout) rootView.findViewById(R.id.ll_desc_spec);
    }

    @Override
    public void onClickRatingBar(SourceSiteModel sourceSiteModel, int pos) {
        showRatingDialog(sourceSiteModel, pos);
    }

    public void showRatingDialog(final SourceSiteModel sourceSiteModel, final int pos) {
        try {

            final Dialog mdialog = new Dialog(getActivity());
            mdialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            mdialog.setContentView(R.layout.service_provider_rating_dialog);
            mdialog.setCancelable(false);
            //mdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Window window = mdialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            window.setAttributes(wlp);
            TextView okButton = (TextView) mdialog.findViewById(R.id.submitButton);
            TextView cancelButton = (TextView) mdialog.findViewById(R.id.cancelButton);

            final EditText commentEditText = (EditText) mdialog.findViewById(R.id.et_comment);
            RatingBar simpleRatingBar = (RatingBar) mdialog.findViewById(R.id.ratingbar); // initiate a rating bar

            simpleRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if (fromUser) {
                        ratingNumber = rating;
                    }
                }
            });

            okButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    JSONObject jsonObject = new JSONObject();
                    PackageDetailsModel packageDetailsModel = ActivityPackageDetails.getInstance().packageDetailsModel;
                    try {
                        jsonObject.put("id", sourceSiteModel.getSourceId());
                        jsonObject.put("packageid", packageDetailsModel.getId());
                        jsonObject.put("comment", "");
                        jsonObject.put("rating", ratingNumber);
                    } catch (JSONException je) {
                        je.printStackTrace();
                    }

                    try {

                        if (new CheckConnection(getActivity()).isConnectedToInternet()) {
                            try {
                                //http://mohanpackaging.com/app/submitrating.php
                                String Url = Constants.BaseUrl + "submitrating.php?packageId=" + packageDetailsModel.getId() + "&comment=Thank you!&rating=" + ratingNumber + "&serviceProviderId=" + sourceSiteModel.getSourceId();
                                getRequestCall(Url, "submit_review", null, pos, sourceSiteModel);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            AlertDialogManager.showAlartDialog(getActivity(), getString(R.string.no_network_title), getString(R.string.no_network_msg));
                        }
                    } catch (Exception je) {
                        je.printStackTrace();
                    }

                    mdialog.dismiss();
                }
            });
            cancelButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    mdialog.dismiss();
                }
            });
            mdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getRequestCall(String url, String tag, JSONObject jsonObject, final int pos, final SourceSiteModel sourceSiteModel) {
        // cancel request from pending queue
        AppController.getInstance().cancelPendingRequests(tag);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());
                        try {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    SourceSiteModel sourceSiteModelNew = new SourceSiteModel();
                                    sourceSiteModelNew.setSourceId(sourceSiteModel.getSourceId());
                                    sourceSiteModelNew.setSourceUrl(sourceSiteModel.getSourceUrl());
                                    if (Validations.isNotNullNotEmptyNotWhiteSpace(sourceSiteModel.getRating())) {
                                        float oldRating = (Float.parseFloat(sourceSiteModel.getRating()) + ratingNumber)/2;
                                        sourceSiteModelNew.setRating("" + oldRating);
                                    } else {
                                        sourceSiteModelNew.setRating("" + ratingNumber);
                                    }
                                    ((SourceRVAdapterDetails) rvRecyclerView.getAdapter()).updateRating(pos, sourceSiteModelNew);
                                }
                            });
                        } catch (Exception ex) {
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
}
