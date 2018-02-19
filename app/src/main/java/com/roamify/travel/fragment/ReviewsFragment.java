package com.roamify.travel.fragment;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
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
import android.widget.RatingBar;
import android.widget.TextView;
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
import com.roamify.travel.activity.ActivityPackageDetails;
import com.roamify.travel.activity.ActivityPackageList;
import com.roamify.travel.adapters.ActivitiesPackageListRVAdapter;
import com.roamify.travel.adapters.ReviewsRVAdapter;
import com.roamify.travel.dialogs.AlertDialogManager;
import com.roamify.travel.models.PackageDetailsModel;
import com.roamify.travel.models.PackageModel;
import com.roamify.travel.models.ReviewModel;
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
public class ReviewsFragment extends Fragment implements View.OnClickListener {
    protected View rootView;
    protected CardView cvRateUsButton;
    protected RecyclerView rvRecyclerView;
    float ratingNumber = 0.0f;
    boolean rateUs = false;
    ArrayList<ReviewModel> arrayList = new ArrayList<>();
    TextView textView;
    String packageId ;
    Bundle bundle = new Bundle();
    public ReviewsFragment() {
        // Required empty public constructor
        //bundle = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_reviews, container, false);
        initView(rootView);
        PackageDetailsModel packageDetailsModel = ActivityPackageDetails.getInstance().packageDetailsModel;
        //http://mohanpackaging.com/app/getReturnReviews.php?packageId=5a2fbdb61f664
        String URL = Constants.BaseUrl + "getReturnReviews.php?packageId="+ActivityPackageDetails.getInstance().packageDetailsModel.getId();
        if (new CheckConnection(getActivity()).isConnectedToInternet()) {
            try {
                getRequestCall(URL, "get_review", null);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            AlertDialogManager.showAlartDialog(getActivity(), getString(R.string.no_network_title), getString(R.string.no_network_msg));
        }

        return rootView;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cv_rateUsButton) {
            showQueryDialog();
        }
    }

    private void initView(View rootView) {
        textView = (TextView)rootView.findViewById(R.id.tv_noreviews);
        cvRateUsButton = (CardView) rootView.findViewById(R.id.cv_rateUsButton);
        cvRateUsButton.setOnClickListener(ReviewsFragment.this);
        rvRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_recyclerView);
        try {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            rvRecyclerView.setLayoutManager(mLayoutManager);
            rvRecyclerView.setHasFixedSize(true);
            rvRecyclerView.setItemAnimator(new DefaultItemAnimator());
        } catch (InflateException ie) {
            ie.getMessage();
        }
    }

    public void showQueryDialog() {
        try {
            final Dialog mdialog = new Dialog(getActivity());
            mdialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            mdialog.setContentView(R.layout.review_submition_dialog);
            mdialog.setCancelable(false);
            //mdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Window window = mdialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            window.setAttributes(wlp);
            TextView okButton = (TextView) mdialog.findViewById(R.id.submitButton);
            TextView cancelButton = (TextView) mdialog.findViewById(R.id.cancelButton);

            final EditText nameEditText = (EditText) mdialog.findViewById(R.id.et_name);
            final EditText emailEditText = (EditText) mdialog.findViewById(R.id.et_email);
            //final EditText phoneEditText = (EditText) mdialog.findViewById(R.id.et_phone);
            final EditText commentEditText = (EditText) mdialog.findViewById(R.id.et_comment);
            RatingBar simpleRatingBar = (RatingBar) mdialog.findViewById(R.id.ratingbar); // initiate a rating bar

            simpleRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if (fromUser) {
                        ratingNumber = rating;
                        rateUs = fromUser;
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
                        jsonObject.put("packageId", packageDetailsModel.getId());
                        jsonObject.put("userName", nameEditText.getText().toString().trim());
                        jsonObject.put("userEmail", emailEditText.getText().toString().trim());
                        jsonObject.put("comment", commentEditText.getText().toString().trim());
                        jsonObject.put("rating", ratingNumber);
                    } catch (JSONException je) {
                        je.printStackTrace();
                    }

                    try {

                        if (new CheckConnection(getActivity()).isConnectedToInternet()) {
                            try {
                                String name = nameEditText.getText().toString().trim();
                                String email = emailEditText.getText().toString().trim();
                                String comment = commentEditText.getText().toString().trim().replaceAll(" ","%20");

                                if (!Validations.emailValidator(email)) {
                                    AlertDialogManager.showAlartDialog(getActivity(), "Alert!", "Please enter valid email address.");
                                    return;
                                }

                                if (!rateUs) {
                                    AlertDialogManager.showAlartDialog(getActivity(), "Alert!", "Please rate this deal.");
                                    return;
                                }

                                String Url = Constants.BaseUrl + "submitReview.php?packageId=" + packageDetailsModel.getId() + "&userName=" + name + "&userEmail=" + email + "&comment=" + comment + "&rating=" + ratingNumber;
                                getRequestCall(Url, "submit_review", null);
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
        if (response.has("details")) {
            JSONArray jsonArray = response.getJSONArray("details");
            for (int i = 0; i < jsonArray.length(); i++) {
                ReviewModel model = new ReviewModel();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String comment = jsonObject.getString("comment");
                model.setReviewId(id);
                model.setReviewName(name);
                model.setReviewComment(comment);
                arrayList.add(model);
            }

            if (arrayList.size() > 0) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setVisibility(View.GONE);
                        rvRecyclerView.setVisibility(View.VISIBLE);
                        rvRecyclerView.setAdapter(new ReviewsRVAdapter(arrayList, getActivity()));
                    }
                });
            }

        } else {
            String status = response.getString("status");
            if (status.equals("1"))
                Toast.makeText(getActivity(), "Thank you to give you precious time.", Toast.LENGTH_SHORT).show();
        }
    }
}
