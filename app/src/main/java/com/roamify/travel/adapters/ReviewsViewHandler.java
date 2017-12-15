package com.roamify.travel.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.roamify.travel.R;

/**
 * Created by Kapil on 28/09/17.
 */

public class ReviewsViewHandler extends RecyclerView.ViewHolder {
    LinearLayout ll_rowLayout;
    TextView tv_Name;
    TextView tv_Comment;

    ReviewsViewHandler(View itemView) {
        super(itemView);
        ll_rowLayout = (LinearLayout) itemView.findViewById(R.id.ll_rowLayout);
        tv_Name = (TextView) itemView.findViewById(R.id.tv_name);
        tv_Comment = (TextView) itemView.findViewById(R.id.tv_comment);
    }
}
