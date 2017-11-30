package com.roamify.travel.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roamify.travel.R;
import com.roamify.travel.activity.HomePage;
import com.roamify.travel.models.MenuItemModel;
import com.roamify.travel.models.StateWiseActivityModel;
import com.roamify.travel.utils.Constants;

import java.util.ArrayList;

/**
 * Created by kapilyadav on 27-Sep-17.
 */

public class DestinationWiseActivityRVAdapter extends RecyclerView.Adapter<DestinationWiseViewHandler> {
    private ArrayList<StateWiseActivityModel> menuItemModel;
    private Activity activity;
    int menuHeight;

    public DestinationWiseActivityRVAdapter(ArrayList<StateWiseActivityModel> menuItemModels, Activity activity, int menuHeight) {
        this.activity = activity;
        this.menuItemModel = menuItemModels;
        this.menuHeight = menuHeight;
    }

    @Override
    public DestinationWiseViewHandler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cell_destinationwise_activity_item, parent, false);
        return new DestinationWiseViewHandler(itemView);
    }

    @Override
    public void onBindViewHolder(DestinationWiseViewHandler holder, final int position) {
        StateWiseActivityModel data = menuItemModel.get(position);

        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        final int width = displayMetrics.widthPixels / 3;
        ViewGroup.LayoutParams layoutParams = holder.ll_rowLayout.getLayoutParams();
        layoutParams.width = width;
        //layoutParams.height = width+200;
        holder.ll_rowLayout.setLayoutParams(layoutParams);

        if (data != null) {
            try {
                holder.tv_Title.setText(data.getActivityName());
            } catch (Exception e) {
                e.getMessage();
            }
            holder.ll_rowLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Constants.activityItemClickListener != null)
                        Constants.activityItemClickListener.onClicked(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return menuItemModel.size();
    }

}