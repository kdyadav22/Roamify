package com.roamify.travel.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.roamify.travel.R;

/**
 * Created by Kapil on 28/09/17.
 */

public class DestinationWiseViewHandler extends RecyclerView.ViewHolder{
    LinearLayout ll_rowLayout;
    TextView tv_Title;
    ImageView imageView;
    DestinationWiseViewHandler(View itemView) {
        super(itemView);
        ll_rowLayout = (LinearLayout) itemView.findViewById(R.id.ll_rowLayout);
        tv_Title = (TextView) itemView.findViewById(R.id.textView);
        imageView = (ImageView)itemView.findViewById(R.id.imageView);
    }
}
