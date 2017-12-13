package com.roamify.travel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.roamify.travel.R;
import com.roamify.travel.activity.ActivityPackageDetails;
import com.roamify.travel.models.PackageDetailsModel;
import com.roamify.travel.utils.Validations;

/**
 * A simple {@link Fragment} subclass.
 */
public class DescriptionFragment extends Fragment {


    protected View rootView;
    protected TextView tvDetailsDuration;
    protected TextView tvDetailsPrice;
    protected TextView tvDetailsSource;
    protected TextView tvDetailsDesc;
    protected TextView tvDetailsSpec;
    protected LinearLayout llDetailsSpec;
    protected TextView tvDetailsFtr;
    protected LinearLayout llDetailsFtr;

    public DescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_description, container, false);
        initView(rootView);

        PackageDetailsModel packageDetailsModel = ActivityPackageDetails.getInstance().packageDetailsModel;
        String pacaDuration = packageDetailsModel.getDuration();
        tvDetailsDuration.setText(pacaDuration);
        String packPrice = packageDetailsModel.getPackagePrice();
        tvDetailsPrice.setText(packPrice);
        String packSrc = packageDetailsModel.getSource();
        tvDetailsSource.setText(packSrc);
        String packDesc = packageDetailsModel.getDescription();
        tvDetailsDesc.setText(packDesc);
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
        tvDetailsSource = (TextView) rootView.findViewById(R.id.tv_details_source);
        tvDetailsDesc = (TextView) rootView.findViewById(R.id.tv_details_desc);
        tvDetailsSpec = (TextView) rootView.findViewById(R.id.tv_details_spec);
        llDetailsSpec = (LinearLayout) rootView.findViewById(R.id.ll_details_spec);
        tvDetailsFtr = (TextView) rootView.findViewById(R.id.tv_details_ftr);
        llDetailsFtr = (LinearLayout) rootView.findViewById(R.id.ll_details_ftr);
    }
}
