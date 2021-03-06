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
import com.roamify.travel.models.PackageModel;
import com.roamify.travel.utils.Constants;

import java.util.ArrayList;

/**
 * Created by kapil yadav on 27-Sep-17.
 */

public class ActivitiesPackageListRVAdapter extends RecyclerView.Adapter<ActivityPackageListViewHandler> {
    private Activity activity;
    private ArrayList<PackageModel> activityModels;
    private String action;

    public ActivitiesPackageListRVAdapter(ArrayList<PackageModel> activityModels, Activity activity, String action) {
        this.activity = activity;
        this.activityModels = activityModels;
        this.action = action;
    }

    @Override
    public ActivityPackageListViewHandler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cell_activity_package_list_changed_layout, parent, false);
        return new ActivityPackageListViewHandler(itemView);
    }

    @Override
    public void onBindViewHolder(ActivityPackageListViewHandler holder, final int position) {
        final PackageModel data = activityModels.get(holder.getAdapterPosition());

        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels / 2;
        int height = displayMetrics.widthPixels / 2;
        android.view.ViewGroup.LayoutParams layoutParamsImageview = holder.packageImageView.getLayoutParams();
        layoutParamsImageview.width = width;
        layoutParamsImageview.height = height;
        holder.packageImageView.setLayoutParams(layoutParamsImageview);

        ViewGroup.LayoutParams layoutParams = holder.rowLayout.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        holder.rowLayout.setLayoutParams(layoutParams);

        /*try {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
            holder.recyclerView.setLayoutManager(mLayoutManager);
            holder.recyclerView.setHasFixedSize(true);
            holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
        } catch (InflateException ie) {
            ie.getMessage();
        }*/

        if (data != null) {
            try {
                /*if (action.equals("PackageList")) {
                    holder.ll_durationNPriceSection.setVisibility(View.VISIBLE);
                }*/
                holder.tv_pkgname.setText(data.getPackageName());

                /*if (Validations.isNotNullNotEmptyNotWhiteSpace(data.getPackageDuration()))
                    holder.tv_pkgduration.setText(data.getPackageDuration());
                if (Validations.isNotNullNotEmptyNotWhiteSpace(data.getPackagePrice()))
                    holder.pkgprice.setText(data.getPackagePrice() + " per person");
                if (Validations.isNotNullNotEmptyNotWhiteSpace(data.getPackageSource())) {
                    holder.recyclerView.setVisibility(View.VISIBLE);
                    //holder.tv_packageSources.setText(data.getPackageSource().replace(",", "\n"));
                    String sources[] = data.getPackageSource().split(",");
                    if (sources.length > 0) {
                        holder.recyclerView.setAdapter(new SourceRVAdapter(sources, activity));
                    }
                }*/
                try {
                    Glide.with(activity)
                            .load(Constants.BaseImageUrl + data.getPackageImageName())
                            //.fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .crossFade(1000)
                            .override(width, height)
                            .error(R.drawable.default_image)
                            .placeholder(R.drawable.default_image)
                            .into(holder.packageImageView);
                } catch (Exception e) {
                    e.fillInStackTrace();
                }

            } catch (Exception e) {
                e.getMessage();
            }

            holder.rowLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Constants.activityItemClickListener != null)
                        Constants.activityItemClickListener.onClicked(data.getPackageId(), data.getPackageName());

                    //For testing
                    Intent intent = new Intent(activity, ActivityPackageDetails.class);
                    intent.putExtra("package_id", data.getPackageId());
                    intent.putExtra("package_name", data.getPackageName());
                    intent.putExtra("isComingFromPkgList", true);
                    activity.startActivity(intent);
                    //activity.finish();
                    activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
            });

            /*holder.recyclerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Constants.activityItemClickListener != null)
                        Constants.activityItemClickListener.onClicked(data.getPackageId(), data.getPackageName());

                    //For testing
                    Intent intent = new Intent(activity, ActivityPackageDetails.class);
                    intent.putExtra("package_id", data.getPackageId());
                    intent.putExtra("package_name", data.getPackageName());
                    intent.putExtra("isComingFromPkgList", true);
                    activity.startActivity(intent);
                    //activity.finish();
                    activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
            });*/

            /*LinkUtils.autoLink(holder.tv_packageSources, new LinkUtils.OnClickListener() {
                @Override
                public void onLinkClicked(final String link) {
                    Log.i("SensibleUrlSpan", "Span Link clicked:" + link);
                }

                @Override
                public void onClicked() {
                    Log.i("SensibleUrlSpan", "Url: ");
                }
            });*/
        }
    }

    @Override
    public int getItemCount() {
        return activityModels.size();
    }

}
