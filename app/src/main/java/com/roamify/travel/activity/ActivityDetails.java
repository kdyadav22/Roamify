package com.roamify.travel.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.InflateException;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.roamify.travel.R;
import com.roamify.travel.adapters.ActivitiesDetailsRVAdapter;
import com.roamify.travel.adapters.ActivitiesRVAdapter;
import com.roamify.travel.models.RawDataModel;
import com.roamify.travel.rawdata.RawData;
import com.roamify.travel.views.NavigationCellView;

public class ActivityDetails extends AppCompatActivity implements View.OnClickListener{
    ListView listView = null;
    ListCellAdapter listCellAdapter = null;
    Activity currentActivity = null;
    RawDataModel rawDataModel = new RawDataModel();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        currentActivity = this;
        findViewById(R.id.right_bar_button).setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.rv_recyclerView);
        try {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(new ActivitiesDetailsRVAdapter(RawData.setPackage(), ActivityDetails.this));

        } catch (InflateException ie) {
            ie.getMessage();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        toolbar.setTitleTextAppearance(this, R.style.NavBarTitle);
        toolbar.setSubtitleTextAppearance(this, R.style.NavBarSubTitle);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.navList);

        try {
            if (getIntent().getBooleanExtra("isComingFromList", false)) {
                rawDataModel.setActivityModelarrayList(RawData.setDestination());
            } else {
                switch (getIntent().hasExtra("title") ? getIntent().getStringExtra("title") : "") {
                    case "WATER ACTIVITIES": {
                        rawDataModel.setActivityModelarrayList(RawData.setWaterActivity());
                        break;
                    }
                    case "AIR ACTIVITIES": {
                        rawDataModel.setActivityModelarrayList(RawData.setAirActivity());
                        break;
                    }
                    case "LAND ACTIVITIES": {
                        rawDataModel.setActivityModelarrayList(RawData.setLandActivity());
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        listCellAdapter = new ListCellAdapter();
        listView.setOnItemClickListener(didSelectedListCell);
        setAdapter();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private AdapterView.OnItemClickListener didSelectedListCell = new AdapterView.OnItemClickListener() {

        public void onItemClick(AdapterView<?> arg0, View arg1, int index,
                                long arg3) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
    };

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.right_bar_button) {
            Intent intent = new Intent(getApplicationContext(),MenuPage.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
        }
    }

    private class ListCellAdapter extends BaseAdapter {

        public int getCount() {

            return rawDataModel.getActivityModelarrayList().size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflateView();
            }
            populateView(convertView, position);
            return convertView;
        }

        private View inflateView() {

            NavigationCellView cellView = new NavigationCellView(currentActivity);

            ViewHolder vh = new ViewHolder();
            vh.thumbnailView = cellView.thumbnailView;
            vh.titleView = cellView.titleView;

            cellView.view.setTag(vh);

            return cellView.view;
        }
    }

    private static class ViewHolder {
        ImageView thumbnailView;
        TextView titleView;
    }

    public void setAdapter() {

        if (listView.getAdapter() == null && listCellAdapter != null) {
            listView.setAdapter(listCellAdapter);
        }
        assert listCellAdapter != null;
        listCellAdapter.notifyDataSetChanged();
    }

    public void populateView(View view, int position) {

        ViewHolder holder = (ViewHolder) view.getTag();
        int imageID = R.drawable.ic_next;
        holder.titleView.setText(rawDataModel.getActivityModelarrayList().get(position).getActivityName());
        holder.thumbnailView.setImageResource(imageID);
    }
}
