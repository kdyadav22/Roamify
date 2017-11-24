package com.roamify.travel.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.roamify.travel.R;

/**
 * Created by Kapil on 28/09/17.
 */

public class SearchViewHandler extends RecyclerView.ViewHolder{
    LinearLayout ll_activity_rowLayout;
    TextView tv_Title;
    SearchViewHandler(View itemView) {
        super(itemView);
        ll_activity_rowLayout = (LinearLayout) itemView.findViewById(R.id.ll_activity_rowLayout);
        tv_Title = (TextView) itemView.findViewById(R.id.tv_activityName);
    }
}
