package com.roamify.travel.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.roamify.travel.R;

/**
 * Created by Kapil on 28/09/17.
 */

public class SourceViewHandler extends RecyclerView.ViewHolder{
    TextView tv_Title;
    RatingBar ratingBar;
    LinearLayout ratingBtn;
    SourceViewHandler(View itemView) {
        super(itemView);
        tv_Title = (TextView) itemView.findViewById(R.id.tv_packageSources);
        ratingBar = (RatingBar) itemView.findViewById(R.id.rbDefaultRatingIndicator);
        ratingBtn = (LinearLayout) itemView.findViewById(R.id.ll_ratingBtn);
    }
}
