package com.roamify.travel.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.roamify.travel.R;

/**
 * Created by Kapil on 28/09/17.
 */

public class TopPackageListViewHandler extends RecyclerView.ViewHolder{
    LinearLayout ll_activity_rowLayout, ll_durationNPriceSection;
    TextView tv_pkgname, tv_pkgduration, pkgprice;
    CardView cardView;
    ImageView packageImageView;
    private TextView tvPackagesubtitle;
    private RatingBar rbDefaultRatingIndicator;
    private TextView tvProdReviews;
    TopPackageListViewHandler(View itemView) {
        super(itemView);
        ll_durationNPriceSection = (LinearLayout) itemView.findViewById(R.id.ll_durationNPriceSection);
        ll_activity_rowLayout = (LinearLayout) itemView.findViewById(R.id.ll_rowLayout);
        tv_pkgname = (TextView) itemView.findViewById(R.id.tv_packagename);
        tv_pkgduration = (TextView) itemView.findViewById(R.id.tv_packageduration);
        pkgprice = (TextView) itemView.findViewById(R.id.tv_packageprice);
        cardView = (CardView) itemView.findViewById(R.id.cardview);
        packageImageView = (ImageView)itemView.findViewById(R.id.iv_activityImage);
        tvPackagesubtitle = (TextView) itemView.findViewById(R.id.tv_packagesubtitle);
        rbDefaultRatingIndicator = (RatingBar) itemView.findViewById(R.id.rbDefaultRatingIndicator);
        tvProdReviews = (TextView) itemView.findViewById(R.id.tv_prodReviews);
    }

}
