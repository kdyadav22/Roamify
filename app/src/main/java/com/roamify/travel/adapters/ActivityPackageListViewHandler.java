package com.roamify.travel.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.roamify.travel.R;

/**
 * Created by Kapil on 28/09/17.
 */

public class ActivityDetailsViewHandler extends RecyclerView.ViewHolder{
    LinearLayout ll_activity_rowLayout;
    TextView tv_pkgname, tv_pkgduration, pkgprice, pkgsubmit;
    CardView cardView;
    ActivityDetailsViewHandler(View itemView) {
        super(itemView);
        ll_activity_rowLayout = (LinearLayout) itemView.findViewById(R.id.ll_rowLayout);
        tv_pkgname = (TextView) itemView.findViewById(R.id.tv_packagename);
        tv_pkgduration = (TextView) itemView.findViewById(R.id.tv_packageduration);
        pkgprice = (TextView) itemView.findViewById(R.id.tv_packageprice);
        pkgsubmit = (TextView) itemView.findViewById(R.id.tv_packagesubmit);
        cardView = (CardView) itemView.findViewById(R.id.cardview);
    }
}
