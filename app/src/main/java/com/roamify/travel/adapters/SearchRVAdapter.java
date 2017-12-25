package com.roamify.travel.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roamify.travel.R;
import com.roamify.travel.models.ActivityModel;
import com.roamify.travel.models.HomePageSearchModel;
import com.roamify.travel.utils.Constants;

import java.util.ArrayList;

/**
 * Created by kapilyadav on 27-Sep-17.
 */

public class SearchRVAdapter extends RecyclerView.Adapter<SearchViewHandler> {
    //private ActivityModel activityModel = new ActivityModel();
    private Activity activity;
    private ArrayList<HomePageSearchModel> activityModels;

    public SearchRVAdapter(ArrayList<HomePageSearchModel> activityModels, Activity activity) {
        this.activity = activity;
        //activityModel.setActivityModels(activityModels);
        this.activityModels = activityModels;
    }

    @Override
    public SearchViewHandler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item, parent, false);
        return new SearchViewHandler(itemView);
    }

    @Override
    public void onBindViewHolder(SearchViewHandler holder, final int position) {
        final HomePageSearchModel data = activityModels.get(position);
        if (data != null) {
            try {
                holder.tv_Title.setText(data.getName());
            } catch (Exception e) {
                e.getMessage();
            }
            holder.ll_activity_rowLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Constants.activityItemClickListener != null)
                        Constants.activityItemClickListener.onClicked(data.getId(), data.getName());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return activityModels.size();
    }

}
