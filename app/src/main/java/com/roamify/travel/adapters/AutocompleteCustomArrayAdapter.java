package com.roamify.travel.adapters;

/**
 * Created by Panalink-03 on 11/27/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.roamify.travel.R;
import com.roamify.travel.models.ActivityModel;
import com.roamify.travel.models.DestinationModel;

import java.util.ArrayList;

public class AutocompleteCustomArrayAdapter extends ArrayAdapter<ActivityModel> {

    final String TAG = "AutocompleteCustomArrayAdapter.java";

    Activity mContext;
    int layoutResourceId;
    ArrayList<ActivityModel> dataArrayList = null;

    public AutocompleteCustomArrayAdapter(Activity mContext, int layoutResourceId, ArrayList<ActivityModel> data) {
        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.dataArrayList = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try{
            /*
             * The convertView argument is essentially a "ScrapView" as described is Lucas post
             * http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/
             * It will have a non-null value when ListView is asking you recycle the row layout.
             * So, when convertView is not null, you should simply update its contents instead of inflating a new row layout.
             */
            if(convertView==null){
                // inflate the layout
                LayoutInflater inflater = mContext.getLayoutInflater();
                convertView = inflater.inflate(layoutResourceId, parent, false);
            }

            // object item based on the position
            ActivityModel objectItem = dataArrayList.get(position);
            // get the TextView and then set the text (item name) and tag (item ID) values
            TextView textViewItem = (TextView) convertView.findViewById(R.id.tv_activityName);
            textViewItem.setText(objectItem.getActivityName());
            // in case you want to add some style, you can do something like:
            //textViewItem.setBackgroundColor(Color.CYAN);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }
}
