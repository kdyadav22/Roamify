package com.roamify.travel.adapters;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.roamify.travel.R;
import com.roamify.travel.models.ActivityModel;
import com.roamify.travel.models.StateWiseActivityModel;
import com.roamify.travel.utils.Constants;

import java.util.ArrayList;

/**
 * Created by kapilyadav on 27-Sep-17.
 */

public class ActivityWiseActivityRVAdapter extends RecyclerView.Adapter<DestinationWiseViewHandler> {
    private ArrayList<ActivityModel> menuItemModel;
    private Activity activity;
    int menuHeight;

    public ActivityWiseActivityRVAdapter(ArrayList<ActivityModel> menuItemModels, Activity activity, int menuHeight) {
        this.activity = activity;
        this.menuItemModel = menuItemModels;
        this.menuHeight = menuHeight;
    }

    @Override
    public DestinationWiseViewHandler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cell_activity_wise_item, parent, false);
        return new DestinationWiseViewHandler(itemView);
    }

    @Override
    public void onBindViewHolder(DestinationWiseViewHandler holder, final int position) {
        final ActivityModel data = menuItemModel.get(position);

        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        final int width = displayMetrics.widthPixels / 4;
        ViewGroup.LayoutParams layoutParams = holder.ll_rowLayout.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = width;
        holder.ll_rowLayout.setLayoutParams(layoutParams);

        if (data != null) {
            try {
                holder.tv_Title.setText(data.getActivityName());
            } catch (Exception e) {
                e.getMessage();
            }

            try {
                Glide.with(activity)
                        .load(Constants.BaseImageUrl + data.getActivityIcon())
                        //.fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .crossFade(1000)
                        .override(width, width)
                        .error(R.drawable.ic_hotel_white_24dp)
                        .placeholder(R.drawable.ic_hotel_white_24dp)
                        .into(holder.imageView);
            } catch (Exception e) {
                e.fillInStackTrace();
            }

            //holder.imageView.setColorFilter(ContextCompat.getColor(activity, R.color.md_green_700));

            holder.ll_rowLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Constants.activityItemClickListener != null)
                        Constants.activityItemClickListener.onClicked(data.getActivityId(), data.getActivityName());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return menuItemModel.size();
    }

}
