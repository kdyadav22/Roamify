package com.roamify.travel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.roamify.travel.R;
import com.roamify.travel.adapters.AutocompleteHomePageArrayAdapter;
import com.roamify.travel.adapters.CustomAutoCompleteView;
import com.roamify.travel.dialogs.AlertDialogManager;
import com.roamify.travel.listeners.ActivityItemClickListener;
import com.roamify.travel.models.HomePageSearchModel;
import com.roamify.travel.models.MenuItemModel;
import com.roamify.travel.utils.AppController;
import com.roamify.travel.utils.CheckConnection;
import com.roamify.travel.utils.Constants;
import com.roamify.travel.utils.Validations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomePageWithMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ActivityItemClickListener, View.OnClickListener {
    String requestTag = "get_all_result";
    protected LinearLayout btWaterActivity;
    protected LinearLayout btAirActivity;
    protected LinearLayout btLandActivity;
    protected LinearLayout btDestination;
    protected ImageView ivWaterActivity;
    protected ImageView ivAirActivity;
    protected ImageView ivLandActivity;
    protected ImageView ivDestinations;
    protected ImageView collapsedImage;
    protected LinearLayout llIconMenu;
    static HomePageWithMenu mInstance;
    protected CustomAutoCompleteView autoCompleteTextView;
    LinearLayout collapsed_toolbar_layout;
    LinearLayout rv_list_portion;
    //RecyclerView mMenuListRecyclerView;
    TextView toolbar_textView;
    TextView whereToSearch;
    RelativeLayout rl_autoSearch;
    CollapsingToolbarLayout collapsingToolbarLayout;
    CoordinatorLayout rootLayout;
    AppBarLayout appBarLayout;

    public static synchronized HomePageWithMenu getInstance() {
        return mInstance;
    }

    int listViewHeight;
    int totalHeight;
    // adapter for auto-complete
    ArrayAdapter<HomePageSearchModel> myAdapter;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    ArrayList<HomePageSearchModel> pageSearchModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_with_menu);
        initView();

        toolbar.setTitleTextAppearance(this, R.style.NavBarTitle);
        toolbar.setSubtitleTextAppearance(this, R.style.NavBarSubTitle);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException npe) {
            npe.getMessage();
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        collapsingToolbarLayout.setTitle(" ");

        /*if (AppController.getInstance().getSearchImage() != null) {
            try {
                Glide.with(this)
                        .load(Constants.BaseImageUrl + AppController.getInstance().getSearchImage())
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .crossFade(1000)
                        //.override(600, 400)
                        .error(R.drawable.default_nav_bar)
                        .placeholder(R.drawable.default_nav_bar)
                        .into(collapsedImage);
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }*/

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    Validations.hideSoftInput(HomePageWithMenu.this);
                    collapsingToolbarLayout.setTitle("");
                    collapsed_toolbar_layout.setVisibility(View.VISIBLE);
                    toolbar_textView.setText("Search for?");
                    llIconMenu.setVisibility(View.VISIBLE);
                    //To hide auto complete text box
                    /*whereToSearch.setVisibility(View.VISIBLE);
                    rl_autoSearch.setVisibility(View.GONE);*/
                    isShow = true;
                } else if (isShow) {
                    collapsed_toolbar_layout.setVisibility(View.GONE);
                    toolbar_textView.setText("Search for?");
                    llIconMenu.setVisibility(View.GONE);
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

        if (new CheckConnection(getApplicationContext()).isConnectedToInternet()) {
            try {
                getRequestCall(Constants.GetAllResultApi, requestTag);
                //mMenuListRecyclerView.setAdapter(new MenuGridRVAdapter(setMenuData(), HomePage.getInstance(), listViewHeight / 4));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            AlertDialogManager.showAlartDialog(HomePageWithMenu.this, getString(R.string.no_network_title), getString(R.string.no_network_msg));
        }
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

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page_with_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        if (id == R.id.nav_mytrips) {
            try {
                /*intent = new Intent(getApplicationContext(), MyTripsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);*/
                Toast.makeText(mInstance, "Work in progress", Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (id == R.id.nav_aboutus) {
            try {
                intent = new Intent(getApplicationContext(), AboutUsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (id == R.id.nav_contactus) {
            try {
                intent = new Intent(getApplicationContext(), ContactUsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_signIn) {
            try {
                intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.putExtra("comingFromHomePage", true);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (id == R.id.nav_logout) {

        }else if (id == R.id.nav_water) {
            try {
                intent = new Intent(getApplicationContext(), ActivitiesList.class);
                intent.putExtra("title", "WATER ACTIVITIES");
                intent.putExtra("type", "Water");
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else if (id == R.id.nav_land) {
            try {
                intent = new Intent(getApplicationContext(), ActivitiesList.class);
                intent.putExtra("title", "LAND ACTIVITIES");
                intent.putExtra("type", "Land");
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else if (id == R.id.nav_air) {
            try {
                intent = new Intent(getApplicationContext(), ActivitiesList.class);
                intent.putExtra("title", "AIR ACTIVITIES");
                intent.putExtra("type", "Air");
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else if (id == R.id.nav_destination) {
            try {
                intent = new Intent(getApplicationContext(), DestinationList.class);
                intent.putExtra("title", "DESTINATIONS");
                intent.putExtra("isComingForDestinationWiseSearch", true);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        navigationView.setNavigationItemSelectedListener(this);
        Constants.activityItemClickListener = HomePageWithMenu.this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Constants.activityItemClickListener = null;
    }

    @Override
    public void onClicked(String id, String name) {}

    private ArrayList<MenuItemModel> setMenuData() {
        ArrayList<MenuItemModel> menuItemModels = new ArrayList<>();

        //Item1
       /* MenuItemModel title1 = new MenuItemModel();
        title1.setTitle(getResources().getString(R.string.hotels));
        title1.setDrawable(R.drawable.hotels_button_bg);
        menuItemModels.add(title1);*/

        //Item2
        MenuItemModel title2 = new MenuItemModel();
        title2.setTitle(getResources().getString(R.string.water_activity));
        title2.setDrawable(R.drawable.water_activity_button_bg);
        menuItemModels.add(title2);

        //Item3
        MenuItemModel title3 = new MenuItemModel();
        title3.setTitle(getResources().getString(R.string.air_activity));
        title3.setDrawable(R.drawable.water_activity_button_bg);
        menuItemModels.add(title3);

        //Item4
        MenuItemModel title4 = new MenuItemModel();
        title4.setTitle(getResources().getString(R.string.land_activity));
        title4.setDrawable(R.drawable.water_activity_button_bg);
        menuItemModels.add(title4);

        //Item5
        MenuItemModel title5 = new MenuItemModel();
        title5.setTitle(getResources().getString(R.string.destination));
        title5.setDrawable(R.drawable.water_activity_button_bg);
        menuItemModels.add(title5);

       /* //Item6
        MenuItemModel title6 = new MenuItemModel();
        title6.setTitle(getResources().getString(R.string.contact_us));
        title6.setDrawable(R.drawable.contact_us_button_bg);
        menuItemModels.add(title6);

        //Item7
        MenuItemModel title7 = new MenuItemModel();
        title7.setTitle(getResources().getString(R.string.about_us));
        title7.setDrawable(R.drawable.about_us_button_bg);
        menuItemModels.add(title7);

        //Item8
        MenuItemModel title8 = new MenuItemModel();
        title8.setTitle(getResources().getString(R.string.faq));
        title8.setDrawable(R.drawable.submit_button_bg);
        menuItemModels.add(title8);*/

        return menuItemModels;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if (view.getId() == R.id.textView) {
            Validations.showSoftInput(HomePageWithMenu.this, autoCompleteTextView);
            autoCompleteTextView.setText("");
            whereToSearch.setVisibility(View.GONE);
            rl_autoSearch.setVisibility(View.VISIBLE);
            autoCompleteTextView.setFocusable(true);
            autoCompleteTextView.requestFocus();
            Validations.showSoftInput(HomePageWithMenu.this, autoCompleteTextView);
        } else if (view.getId() == R.id.toolbar_textView) {
            Validations.showSoftInput(HomePageWithMenu.this, autoCompleteTextView);
            autoCompleteTextView.setText("");
            whereToSearch.setVisibility(View.GONE);
            rl_autoSearch.setVisibility(View.VISIBLE);
            appBarLayout.setExpanded(true, true);
            autoCompleteTextView.setFocusable(true);
            autoCompleteTextView.requestFocus();
            Validations.showSoftInput(HomePageWithMenu.this, autoCompleteTextView);
        } else if (view.getId() == R.id.bt_waterActivity) {
            try {
                intent = new Intent(getApplicationContext(), ActivitiesList.class);
                intent.putExtra("title", "WATER ACTIVITIES");
                intent.putExtra("type", "Water");
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (view.getId() == R.id.bt_airActivity) {
            try {
                intent = new Intent(getApplicationContext(), ActivitiesList.class);
                intent.putExtra("title", "AIR ACTIVITIES");
                intent.putExtra("type", "Air");
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (view.getId() == R.id.bt_landActivity) {
            try {
                intent = new Intent(getApplicationContext(), ActivitiesList.class);
                intent.putExtra("title", "LAND ACTIVITIES");
                intent.putExtra("type", "Land");
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (view.getId() == R.id.bt_destination) {
            try {
                intent = new Intent(getApplicationContext(), DestinationList.class);
                intent.putExtra("title", "DESTINATIONS");
                intent.putExtra("isComingForDestinationWiseSearch", true);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (view.getId() == R.id.iv_waterActivity) {
            try {
                intent = new Intent(getApplicationContext(), ActivitiesList.class);
                intent.putExtra("title", "WATER ACTIVITIES");
                intent.putExtra("type", "Water");
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (view.getId() == R.id.iv_airActivity) {
            try {
                intent = new Intent(getApplicationContext(), ActivitiesList.class);
                intent.putExtra("title", "AIR ACTIVITIES");
                intent.putExtra("type", "Air");
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (view.getId() == R.id.iv_landActivity) {
            try {
                intent = new Intent(getApplicationContext(), ActivitiesList.class);
                intent.putExtra("title", "LAND ACTIVITIES");
                intent.putExtra("type", "Land");
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (view.getId() == R.id.iv_destinations) {
            try {
                intent = new Intent(getApplicationContext(), DestinationList.class);
                intent.putExtra("title", "DESTINATIONS");
                intent.putExtra("isComingForDestinationWiseSearch", true);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void initView() {
        collapsedImage = (ImageView) findViewById(R.id.image);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        autoCompleteTextView = (CustomAutoCompleteView) findViewById(R.id.autoCompleteTextView);
        //mMenuListRecyclerView = (RecyclerView) findViewById(R.id.rv_menu);
        rv_list_portion = (LinearLayout) findViewById(R.id.ll_activity_rowLayout);//
        collapsed_toolbar_layout = (LinearLayout) findViewById(R.id.collapsed_toolbar_layout);
        whereToSearch = (TextView) findViewById(R.id.textView);
        rl_autoSearch = (RelativeLayout) findViewById(R.id.rl_autoSearch);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_textView = (TextView) findViewById(R.id.toolbar_textView);
        rootLayout = (CoordinatorLayout) findViewById(R.id.rootLayout);

        /*RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mMenuListRecyclerView.setLayoutManager(mLayoutManager);
        mMenuListRecyclerView.setHasFixedSize(true);
        mMenuListRecyclerView.setItemAnimator(new DefaultItemAnimator());*/

        collapsingToolbarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validations.hideSoftInput(HomePageWithMenu.this);
            }
        });

        if (AppController.getInstance().getSearchText() != null) {
            whereToSearch.setText("Search for?");
        }
        whereToSearch.setOnClickListener(this);
        toolbar_textView.setOnClickListener(this);
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    myAdapter.notifyDataSetChanged();
                    myAdapter = new AutocompleteHomePageArrayAdapter(HomePageWithMenu.this, R.layout.autocomplete_text_layout, filter(charSequence.toString()));
                    autoCompleteTextView.setAdapter(myAdapter);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                LinearLayout rl = (LinearLayout) arg1;
                LinearLayout rl1 = (LinearLayout) rl.getChildAt(0);
                LinearLayout rl2 = (LinearLayout) rl1.getChildAt(1);
                TextView tv_main = (TextView) rl2.getChildAt(0);
                TextView tv_pos = (TextView) rl2.getChildAt(1);

                whereToSearch.setVisibility(View.VISIBLE);
                rl_autoSearch.setVisibility(View.GONE);
                //whereToSearch.setText(tv_main.getText().toString());
                //toolbar_textView.setText(tv_main.getText().toString());

                AppController.getInstance().setSearchText(tv_main.getText().toString());
                String position = tv_pos.getText().toString();
                if (Validations.isNotNullNotEmptyNotWhiteSpace(position)) {
                    AppController.getInstance().setSearchImage(pageSearchModelArrayList.get(Integer.parseInt(position)).getMainImage());
                    AppController.getInstance().setSearchText(pageSearchModelArrayList.get(Integer.parseInt(position)).getName());
                    sendToNext(Integer.parseInt(position));
                }
            }
        });
        btWaterActivity = (LinearLayout) findViewById(R.id.bt_waterActivity);
        btWaterActivity.setOnClickListener(HomePageWithMenu.this);
        btAirActivity = (LinearLayout) findViewById(R.id.bt_airActivity);
        btAirActivity.setOnClickListener(HomePageWithMenu.this);
        btLandActivity = (LinearLayout) findViewById(R.id.bt_landActivity);
        btLandActivity.setOnClickListener(HomePageWithMenu.this);
        btDestination = (LinearLayout) findViewById(R.id.bt_destination);
        btDestination.setOnClickListener(HomePageWithMenu.this);
        ivWaterActivity = (ImageView) findViewById(R.id.iv_waterActivity);
        ivWaterActivity.setOnClickListener(HomePageWithMenu.this);
        ivAirActivity = (ImageView) findViewById(R.id.iv_airActivity);
        ivAirActivity.setOnClickListener(HomePageWithMenu.this);
        ivLandActivity = (ImageView) findViewById(R.id.iv_landActivity);
        ivLandActivity.setOnClickListener(HomePageWithMenu.this);
        ivDestinations = (ImageView) findViewById(R.id.iv_destinations);
        ivDestinations.setOnClickListener(HomePageWithMenu.this);
        llIconMenu = (LinearLayout) findViewById(R.id.ll_iconMenu);
    }

    private ArrayList<HomePageSearchModel> filter(String folderID) {
        final ArrayList<HomePageSearchModel> filteredModelList = new ArrayList<>();
        for (int i = 0; i < pageSearchModelArrayList.size(); i++) {
            HomePageSearchModel model = new HomePageSearchModel();
            final String fId = pageSearchModelArrayList.get(i).getName().toLowerCase();
            if (fId.startsWith(folderID.toLowerCase())) {
                model.setId(pageSearchModelArrayList.get(i).getId());
                model.setName(pageSearchModelArrayList.get(i).getName());
                model.setType(pageSearchModelArrayList.get(i).getType());
                model.setMainImage(pageSearchModelArrayList.get(i).getMainImage());
                model.setPosition(pageSearchModelArrayList.get(i).getPosition());
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    private void sendToNext(int pos) {
        Validations.hideSoftInput(HomePageWithMenu.this);
        try {
            //If item is location type then will go on "All Activites Page"
            //If item is activity type then will go on "Destination List Page"
            whereToSearch.setVisibility(View.VISIBLE);
            rl_autoSearch.setVisibility(View.GONE);
            if (pageSearchModelArrayList.get(pos).getType().equals("Activity")) {
                Intent intent = new Intent(getApplicationContext(), DestinationList.class);
                intent.putExtra("title", pageSearchModelArrayList.get(pos).getName());
                intent.putExtra("id", pageSearchModelArrayList.get(pos).getId());
                intent.putExtra("isComingFromSearchPage", true);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } else if (pageSearchModelArrayList.get(pos).getType().equals("Location")) {
                Intent intent = new Intent(getApplicationContext(), AllActivities.class);
                intent.putExtra("title", pageSearchModelArrayList.get(pos).getName());
                intent.putExtra("loc_id", pageSearchModelArrayList.get(pos).getId());
                intent.putExtra("isComingFromSearchPage", true);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } else if (pageSearchModelArrayList.get(pos).getType().equals("Package")) {
                Intent intent = new Intent(getApplicationContext(), ActivityPackageDetails.class);
                intent.putExtra("title", pageSearchModelArrayList.get(pos).getName());
                intent.putExtra("package_id", pageSearchModelArrayList.get(pos).getId());
                //intent.putExtra("loc_id", pageSearchModelArrayList.get(pos).getId());
                //intent.putExtra("act_id", pageSearchModelArrayList.get(pos).getId());
                intent.putExtra("isComingFromSearchPage", true);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } else if (pageSearchModelArrayList.get(pos).getType().equals("State")) {
                Intent intent = new Intent(getApplicationContext(), DestinationList.class);
                intent.putExtra("title", pageSearchModelArrayList.get(pos).getName());
                intent.putExtra("state_id", pageSearchModelArrayList.get(pos).getId());
                intent.putExtra("isComingFromSearchPage", true);
                intent.putExtra("isComingFromSearchPageWithState", true);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }else if (pageSearchModelArrayList.get(pos).getType().equals("Zone")) {
                /*Intent intent = new Intent(getApplicationContext(), DestinationList.class);
                intent.putExtra("title", pageSearchModelArrayList.get(pos).getName());
                intent.putExtra("state_id", pageSearchModelArrayList.get(pos).getId());
                intent.putExtra("isComingFromSearchPage", true);
                intent.putExtra("isComingFromSearchPageWithState", true);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);*/
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getRequestCall(String url, String tag) {
        // cancel request from pending queue
        AppController.getInstance().cancelPendingRequests(tag);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());
                        try {
                            runOnMainThread(response);
                        } catch (JSONException ex) {
                            ex.getMessage();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
            }
        })

        {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        //Adding policy for socket time out
        RetryPolicy policy = new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjReq.setRetryPolicy(policy);

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag);
    }

    private void runOnMainThread(JSONObject response) throws JSONException {
        JSONArray jsonArray = response.getJSONArray("details");
        for (int i = 0; i < jsonArray.length(); i++) {
            HomePageSearchModel homePageSearchModel = new HomePageSearchModel();
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            String type = jsonObject.getString("type");

            homePageSearchModel.setId(id);
            homePageSearchModel.setName(name);
            homePageSearchModel.setType(type);
            if (jsonObject.has("mainImage")) {
                String mainImage = jsonObject.getString("mainImage");
                homePageSearchModel.setMainImage(mainImage);
            }
            homePageSearchModel.setPosition(i);
            pageSearchModelArrayList.add(homePageSearchModel);
        }

        try {
            myAdapter = new AutocompleteHomePageArrayAdapter(this, R.layout.autocomplete_text_layout, pageSearchModelArrayList);
            autoCompleteTextView.setAdapter(myAdapter);
        } catch (Exception Ex) {
            Ex.printStackTrace();
        }
    }
}
