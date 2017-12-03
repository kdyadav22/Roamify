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

public class DestinationViewHandler extends RecyclerView.ViewHolder{
    LinearLayout ll_destination_rowLayout;
    TextView tv_destinationName;
    TextView tv_destinationId;
    ImageView iv_destinationImage;
    DestinationViewHandler(View itemView) {
        super(itemView);
        ll_destination_rowLayout = (LinearLayout) itemView.findViewById(R.id.ll_destination_rowLayout);
        tv_destinationName = (TextView) itemView.findViewById(R.id.tv_destinationName);
        tv_destinationId = (TextView) itemView.findViewById(R.id.tv_destinationId);
        iv_destinationImage = (ImageView) itemView.findViewById(R.id.iv_destinationImage);
    }
}
