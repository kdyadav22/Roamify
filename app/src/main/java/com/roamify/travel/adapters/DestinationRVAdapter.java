package com.roamify.travel.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roamify.travel.R;
import com.roamify.travel.activity.ActivitiesList;
import com.roamify.travel.models.ActivityModel;
import com.roamify.travel.utils.Constants;

import java.util.ArrayList;

/**
 * Created by kapilyadav on 27-Sep-17.
 */

public class DestinationRVAdapter extends RecyclerView.Adapter<DestinationViewHandler> {
    //private ActivityModel activityModel = new ActivityModel();
    private Activity activity;
    private ArrayList<ActivityModel> activityModels;
    public DestinationRVAdapter(ArrayList<ActivityModel> activityModels, Activity activity) {
        this.activity = activity;
        //activityModel.setActivityModels(activityModels);
        this.activityModels = activityModels;
    }

    @Override
    public DestinationViewHandler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.destination_list_item, parent, false);
        return new DestinationViewHandler(itemView);
    }

    @Override
    public void onBindViewHolder(DestinationViewHandler holder, final int position) {
        final ActivityModel data = activityModels.get(position);
        if (data != null) {
            try {
                holder.tv_Title.setText(data.getActivityName());
            } catch (Exception e) {
                e.getMessage();
            }
            holder.ll_activity_rowLayout.setOnClickListener(new View.OnClickListener() {
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
