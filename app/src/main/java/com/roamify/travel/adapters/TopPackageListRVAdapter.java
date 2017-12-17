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
import com.roamify.travel.activity.ActivityPackageList;
import com.roamify.travel.models.PackageModel;
import com.roamify.travel.utils.AppController;
import com.roamify.travel.utils.Constants;

import java.util.ArrayList;

/**
 * Created by kapilyadav on 27-Sep-17.
 */

public class TopPackageListRVAdapter extends RecyclerView.Adapter<TopPackageListViewHandler> {
    private Activity activity;
    private ArrayList<PackageModel> activityModels;
    private String action;

    public TopPackageListRVAdapter(ArrayList<PackageModel> activityModels, Activity activity, String action) {
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
        final PackageModel data = activityModels.get(holder.getAdapterPosition());
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        final int width = displayMetrics.widthPixels/2;
        ViewGroup.LayoutParams layoutParams = holder.ll_activity_rowLayout.getLayoutParams();
        layoutParams.width = width-10;
        //layoutParams.height = width/2;
        holder.ll_activity_rowLayout.setLayoutParams(layoutParams);
        if (data != null) {
            try {
                if (action.equals("PackageList")) {
                    holder.ll_durationNPriceSection.setVisibility(View.VISIBLE);
                }
                holder.tv_pkgname.setText(data.getPackageName());
                holder.tv_pkgduration.setText(data.getPackageDuration());
                holder.pkgprice.setText(data.getPackagePrice());
            } catch (Exception e) {
                e.getMessage();
            }

            try {
                Glide.with(activity)
                        .load(Constants.BaseImageUrl + data.getPackageImageName())
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .crossFade(1000)
                        .override(width-10, width-10)
                        .error(R.drawable.no_image_found)
                        .placeholder(R.drawable.no_image_found)
                        .into(holder.packageImageView);
            } catch (Exception e) {
                e.fillInStackTrace();
            }

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Constants.activityItemClickListener != null)
                        Constants.activityItemClickListener.onClicked(position);

                    //For testing
                    Intent intent = new Intent(activity, ActivityPackageList.class);
                    intent.putExtra("title", data.getPackageName());
                    intent.putExtra("id", data.getPackageId());
                    intent.putExtra("isComingFromSearchPage", true);
                    activity.startActivity(intent);
                    activity.finish();
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
