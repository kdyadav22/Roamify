package com.roamify.travel.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.roamify.travel.R;

/**
 * Created by kapilyadav on 27-Sep-17.
 */

public class MenuViewHandler extends RecyclerView.ViewHolder {
    LinearLayout ll_rowLayout;
    private ImageView iv_Icon;
    TextView tv_Title;
    public MenuViewHandler(View itemView) {
        super(itemView);
        ll_rowLayout = (LinearLayout) itemView.findViewById(R.id.ll_rowLayout);
        iv_Icon = (ImageView) itemView.findViewById(R.id.imageView);
        tv_Title = (TextView) itemView.findViewById(R.id.textView);
    }
}
