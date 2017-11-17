package com.roamify.travel.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.roamify.travel.R;


public class NavigationCellView {

	public View view;

	public ImageView thumbnailView;
	public TextView titleView;
	

	public NavigationCellView(Context ctx){
		
		view = LayoutInflater.from(ctx).inflate(R.layout.view_cell_navigation, null);

		thumbnailView = (ImageView)view.findViewById(R.id.navigation_cell_thumbnail);
		titleView = (TextView)view.findViewById(R.id.navigation_cell_title);
		
	}
	
}
