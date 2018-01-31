package com.roamify.travel.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.roamify.travel.R;
import com.roamify.travel.activity.ActivityPackageDetails;
import com.roamify.travel.activity.AllActivities;
import com.roamify.travel.models.DestinationModel;
import com.roamify.travel.models.PackageModel;
import com.roamify.travel.utils.Constants;

import java.util.ArrayList;

/**
 * Created by kapilyadav on 27-Sep-17.
 */

public class TopDestinationListRVAdapter extends RecyclerView.Adapter<TopPackageListViewHandler> {
    private Activity activity;
    private ArrayList<DestinationModel> activityModels;
    private String action;

    public TopDestinationListRVAdapter(ArrayList<DestinationModel> activityModels, Activity activity, String action) {
        this.activity = activity;
        this.activityModels = activityModels;
        this.action = action;
    }

    @Override
    public TopPackageListViewHandler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cell_top_package_list, parent, false);
        return new TopPackageListViewHandler(itemView);
    }

    @Override
    public void onBindViewHolder(TopPackageListViewHandler holder, final int position) {
        final DestinationModel data = activityModels.get(holder.getAdapterPosition());
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        final int width = displayMetrics.widthPixels / 2;
        ViewGroup.LayoutParams layoutParams = holder.ll_activity_rowLayout.getLayoutParams();
        layoutParams.width = width - 10;
        layoutParams.height = width + 100;
        holder.ll_activity_rowLayout.setLayoutParams(layoutParams);
        if (data != null) {
            try {
                holder.ll_durationNPriceSection.setVisibility(View.GONE);
                holder.tv_pkgname.setText(data.getDestinationName());
            } catch (Exception e) {
                e.getMessage();
            }

            ViewGroup.LayoutParams layoutParamsImage = holder.packageImageView.getLayoutParams();
            layoutParamsImage.width = width - 10;
            layoutParamsImage.height = width - 10;
            holder.packageImageView.setLayoutParams(layoutParamsImage);

            try {
                Glide.with(activity)
                        .load(Constants.BaseImageUrl + data.getDestinationImage())
                        //.fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .crossFade(1000)
                        .override(width, width)
                        .error(R.drawable.default_image)
                        .placeholder(R.drawable.default_image)
                        .into(holder.packageImageView);
            } catch (Exception e) {
                e.fillInStackTrace();
            }

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, AllActivities.class);
                    intent.putExtra("loc_name", data.getDestinationName());
                    intent.putExtra("loc_id", data.getDestinationId());
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return activityModels.size();
    }

}
