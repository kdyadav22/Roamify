package com.roamify.travel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.roamify.travel.R;
import com.roamify.travel.activity.ActivityPackageDetails;
import com.roamify.travel.adapters.SourceRVAdapter;
import com.roamify.travel.adapters.SourceRVAdapterDetails;
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
    protected LinearLayout llDetailsDuration;
    protected LinearLayout llDetailsPrice;
    protected LinearLayout llDetailsSource;
    protected RecyclerView rvRecyclerView;
    protected LinearLayout llDescSpec;

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
        if (Validations.isNotNullNotEmptyNotWhiteSpace(pacaDuration)) {
            llDetailsDuration.setVisibility(View.VISIBLE);
            tvDetailsDuration.setText(pacaDuration);
        }

        String packPrice = packageDetailsModel.getPackagePrice();
        if (Validations.isNotNullNotEmptyNotWhiteSpace(packPrice)) {
            llDetailsPrice.setVisibility(View.VISIBLE);
            tvDetailsPrice.setText("Rs. " + packPrice + " per person");
        }

        String packSrc = packageDetailsModel.getSource();
        if (Validations.isNotNullNotEmptyNotWhiteSpace(packSrc)) {
            try {
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                rvRecyclerView.setLayoutManager(mLayoutManager);
                rvRecyclerView.setHasFixedSize(true);
                rvRecyclerView.setItemAnimator(new DefaultItemAnimator());
            } catch (InflateException ie) {
                ie.getMessage();
            }

            rvRecyclerView.setVisibility(View.VISIBLE);
            //holder.tv_packageSources.setText(data.getPackageSource().replace(",", "\n"));
            String sources[] = packSrc.split(",");
            if (sources.length > 0) {
                rvRecyclerView.setAdapter(new SourceRVAdapterDetails(sources, getActivity()));
            }

            llDetailsSource.setVisibility(View.VISIBLE);
            //tvDetailsSource.setText(packSrc);
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
}
