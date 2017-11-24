package com.roamify.travel.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roamify.travel.R;
import com.roamify.travel.activity.ActivitiesList;
import com.roamify.travel.activity.ActivityPackageDetails;
import com.roamify.travel.dialogs.AlertDialogManager;
import com.roamify.travel.models.PackageModel;

import java.util.ArrayList;

/**
 * Created by kapilyadav on 27-Sep-17.
 */

public class ActivitiesDetailsRVAdapter extends RecyclerView.Adapter<ActivityDetailsViewHandler> {
    private Activity activity;
    ArrayList<PackageModel> activityModels;

    public ActivitiesDetailsRVAdapter(ArrayList<PackageModel> activityModels, Activity activity) {
        this.activity = activity;
        //activityModel.setActivityModels(activityModels);
        this.activityModels = activityModels;
    }

    @Override
    public ActivityDetailsViewHandler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_package_item, parent, false);
        return new ActivityDetailsViewHandler(itemView);
    }

    @Override
    public void onBindViewHolder(ActivityDetailsViewHandler holder, final int position) {
        final PackageModel data = activityModels.get(holder.getAdapterPosition());
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        final int width = displayMetrics.widthPixels / 2;
        ViewGroup.LayoutParams layoutParams = holder.ll_activity_rowLayout.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = width;
        holder.ll_activity_rowLayout.setLayoutParams(layoutParams);
        if (data != null) {
            try {
                holder.tv_pkgname.setText(data.getPackageName());
                holder.tv_pkgduration.setText(data.getPackageDuration());
                holder.pkgprice.setText(data.getPackagePrice());
            } catch (Exception e) {
                e.getMessage();
            }
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ActivitiesList.activityItemClickListener != null)
                        ActivitiesList.activityItemClickListener.activityClicked(position);

                    //For testing
                    Intent intent = new Intent(activity, ActivityPackageDetails.class);
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
            });

            holder.pkgsubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialogManager().showQueryDialog(activity);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return activityModels.size();
    }

}
