package com.roamify.travel.adapters;

/**
 * Created by Kapil Deo Yadav on 2/26/2016
 * Panalinks Infotech Ltd
 */
import android.app.Activity;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.roamify.travel.R;

public class ShareIntentListAdapter extends ArrayAdapter
{
    Activity context;
    Object[] items;
    boolean[] arrows;
    int layoutId;

    public ShareIntentListAdapter(Activity context, int layoutId, Object[] items) {
        super(context, layoutId, items);

        this.context = context;
        this.items = items;
        this.layoutId = layoutId;
    }

    public View getView(int pos, View convertView, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View row = inflater.inflate(layoutId, null);
        TextView label = (TextView) row.findViewById(R.id.shareName);
        ImageView image = (ImageView) row.findViewById(R.id.shareImage);
        label.setText(((ResolveInfo)items[pos]).activityInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());
        image.setImageDrawable(((ResolveInfo)items[pos]).activityInfo.applicationInfo.loadIcon(context.getPackageManager()));

        return(row);
    }
}
