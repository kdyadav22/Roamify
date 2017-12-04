package com.roamify.travel.adapters;

/**
 * Created by Panalink-03 on 11/27/2017.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.roamify.travel.R;
import com.roamify.travel.models.DestinationModel;

import java.util.ArrayList;

public class AutocompleteDestinationAdapter extends ArrayAdapter<DestinationModel> {
    final String TAG = "AutocompleteDestinationAdapter.java";
    Activity mContext;
    int layoutResourceId;
    ArrayList<DestinationModel> dataArrayList = null;
    public AutocompleteDestinationAdapter(Activity mContext, int layoutResourceId, ArrayList<DestinationModel> data) {
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
            DestinationModel objectItem = dataArrayList.get(position);
            // get the TextView and then set the text (item name) and tag (item ID) values
            TextView textViewItem = (TextView) convertView.findViewById(R.id.tv_activityName);
            TextView textViewItemPosition = (TextView) convertView.findViewById(R.id.tv_secondary_text);
            textViewItemPosition.setText(""+position);
            textViewItem.setText(objectItem.getDestinationName());
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
