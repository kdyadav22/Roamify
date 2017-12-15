package com.roamify.travel.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roamify.travel.R;
import com.roamify.travel.models.HomePageSearchModel;
import com.roamify.travel.models.ReviewModel;
import com.roamify.travel.utils.Constants;

import java.util.ArrayList;

/**
 * Created by kapilyadav on 27-Sep-17.
 */

public class ReviewsRVAdapter extends RecyclerView.Adapter<ReviewsViewHandler> {
    private Activity activity;
    private ArrayList<ReviewModel> reviewModels;

    public ReviewsRVAdapter(ArrayList<ReviewModel> reviewModels, Activity activity) {
        this.activity = activity;
        this.reviewModels = reviewModels;
    }

    @Override
    public ReviewsViewHandler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cell_reviews, parent, false);
        return new ReviewsViewHandler(itemView);
    }

    @Override
    public void onBindViewHolder(ReviewsViewHandler holder, final int position) {
        final ReviewModel data = reviewModels.get(position);
        if (data != null) {
            try {
                holder.tv_Name.setText(data.getReviewName());
                holder.tv_Comment.setText(data.getReviewComment());
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    @Override
    public int getItemCount() {
        return reviewModels.size();
    }

}
