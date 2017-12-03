package com.roamify.travel.fragment;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.roamify.travel.R;
import com.roamify.travel.activity.ActivityPackageDetails;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewsFragment extends Fragment implements View.OnClickListener {


    protected View rootView;
    protected CardView cvRateUsButton;
    protected RecyclerView rvRecyclerView;

    public ReviewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_reviews, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cv_rateUsButton) {
            showQueryDialog();
        }
    }

    private void initView(View rootView) {
        cvRateUsButton = (CardView) rootView.findViewById(R.id.cv_rateUsButton);
        cvRateUsButton.setOnClickListener(ReviewsFragment.this);
        rvRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_recyclerView);
    }

    public void showQueryDialog() {
        try {
            final Dialog mdialog = new Dialog(getActivity());
            mdialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            mdialog.setContentView(R.layout.review_submition_dialog);
            mdialog.setCancelable(false);
            mdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Window window = mdialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            window.setAttributes(wlp);
            TextView okButton = (TextView) mdialog.findViewById(R.id.submitButton);
            TextView cancelButton = (TextView) mdialog.findViewById(R.id.cancelButton);

            /*TextView title = (TextView) mdialog.findViewById(R.id.title);
            TextView message = (TextView) mdialog.findViewById(R.id.msg);
            message.setText(msg);*/

            okButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
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
}
