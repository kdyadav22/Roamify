package com.roamify.travel.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roamify.travel.R;
import com.roamify.travel.activity.Website;
import com.roamify.travel.models.SourceSiteModel;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by kapilyadav on 27-Sep-17.
 */

public class SourceRVAdapterDetails extends RecyclerView.Adapter<SourceViewHandler> {
    private Activity activity;
    private ArrayList<SourceSiteModel> activityModels;
    String sources[];

    public SourceRVAdapterDetails(String activityModels[], Activity activity) {
        this.activity = activity;
        //this.activityModels = activityModels;
        sources = activityModels;
    }

    @Override
    public SourceViewHandler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cell_source_list_on_descrition, parent, false);
        return new SourceViewHandler(itemView);
    }

    @Override
    public void onBindViewHolder(SourceViewHandler holder, final int position) {
        //final SourceSiteModel data = activityModels.get(position);
        final String source = sources[position].replace("\r\n","");
        if (source != null) {
            try {
                //String ss = source.substring(0, source.lastIndexOf(".com/")+4);

                URL url = new URL(source);
                String protocal = url.getProtocol();
                String hostName = url.getHost();
                //holder.tv_Title.setText(protocal+"://"+hostName);
                holder.tv_Title.setText(hostName);
            } catch (Exception e) {
                e.getMessage();
            }
            holder.tv_Title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(activity, Website.class);
                    browserIntent.putExtra("WEB_URL", source);
                    //browserIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(browserIntent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return sources.length;
    }

}
