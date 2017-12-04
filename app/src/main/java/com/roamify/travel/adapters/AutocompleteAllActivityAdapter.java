package com.roamify.travel.adapters;

/**
 * Created by Panalink-03 on 11/27/2017.
 */

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.roamify.travel.R;
import com.roamify.travel.models.StateWiseActivityModel;

import java.util.ArrayList;

public class AutocompleteAllActivityAdapter extends ArrayAdapter<StateWiseActivityModel> {
    final String TAG = "AutocompleteAllActivityAdapter.java";
    private Activity mContext;
    private int layoutResourceId;
    private ArrayList<StateWiseActivityModel> dataArrayList = null;
    public AutocompleteAllActivityAdapter(Activity mContext, int layoutResourceId, ArrayList<StateWiseActivityModel> data) {
        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.dataArrayList = data;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
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
            StateWiseActivityModel objectItem = dataArrayList.get(position);
            // get the TextView and then set the text (item name) and tag (item ID) values
            TextView textViewItem = (TextView) convertView.findViewById(R.id.tv_main_text);
            TextView textViewItemPosition = (TextView) convertView.findViewById(R.id.tv_secondary_text);
            textViewItemPosition.setText(objectItem.getActivityId());
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
