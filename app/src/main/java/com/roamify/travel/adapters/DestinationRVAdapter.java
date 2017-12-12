package com.roamify.travel.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.roamify.travel.R;
import com.roamify.travel.activity.ActivitiesList;
import com.roamify.travel.models.DestinationModel;
import com.roamify.travel.utils.AppController;
import com.roamify.travel.utils.Constants;

import java.util.ArrayList;

/**
 * Created by kapilyadav on 27-Sep-17.
 */

public class DestinationRVAdapter extends RecyclerView.Adapter<DestinationViewHandler> {
    private Activity activity;
    private ArrayList<DestinationModel> activityModels;
    public DestinationRVAdapter(ArrayList<DestinationModel> activityModels, Activity activity) {
        this.activity = activity;
        this.activityModels = activityModels;
    }

    @Override
    public DestinationViewHandler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.destination_list_item, parent, false);
        return new DestinationViewHandler(itemView);
    }

    @Override
    public void onBindViewHolder(DestinationViewHandler holder, final int position) {
        final DestinationModel data = activityModels.get(position);
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        final int width = displayMetrics.widthPixels/3;

        android.view.ViewGroup.LayoutParams layoutParamsImageview = holder.iv_destinationImage.getLayoutParams();
        layoutParamsImageview.width = width;
        layoutParamsImageview.height = width;
        holder.iv_destinationImage.setLayoutParams(layoutParamsImageview);

        ViewGroup.LayoutParams layoutParams = holder.ll_destination_rowLayout.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = width+50;
        holder.ll_destination_rowLayout.setLayoutParams(layoutParams);


        try {
            Glide.with(activity)
                    .load(Constants.BaseImageUrl + data.getDestinationImage())
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade(1000)
                    .override(width, width)
                    .error(R.drawable.no_image_found)
                    .placeholder(R.drawable.no_image_found)
                    .into(holder.iv_destinationImage);
        } catch (Exception e) {
            e.fillInStackTrace();
        }


        if (data != null) {
            try {
                holder.tv_destinationName.setText(data.getDestinationName());
            } catch (Exception e) {
                e.getMessage();
            }
            holder.ll_destination_rowLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Constants.activityItemClickListener.onClicked(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return activityModels.size();
    }

}
