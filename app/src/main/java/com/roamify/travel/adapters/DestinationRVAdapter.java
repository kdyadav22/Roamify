package com.roamify.travel.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roamify.travel.R;
import com.roamify.travel.activity.ActivitiesList;
import com.roamify.travel.models.ActivityModel;

import java.util.ArrayList;

/**
 * Created by kapilyadav on 27-Sep-17.
 */

public class ActivitiesRVAdapter extends RecyclerView.Adapter<ActivityViewHandler> {
    //private ActivityModel activityModel = new ActivityModel();
    private Activity activity;
    ArrayList<ActivityModel> activityModels;
    public ActivitiesRVAdapter(ArrayList<ActivityModel> activityModels, Activity activity) {
        this.activity = activity;
        //activityModel.setActivityModels(activityModels);
        this.activityModels = activityModels;
    }

    @Override
    public ActivityViewHandler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_item, parent, false);
        return new ActivityViewHandler(itemView);
    }

    @Override
    public void onBindViewHolder(ActivityViewHandler holder, final int position) {
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
                    ActivitiesList.activityItemClickListener.activityClicked(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return activityModels.size();
    }

}
