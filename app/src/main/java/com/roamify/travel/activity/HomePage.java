package com.roamify.travel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.roamify.travel.R;
import com.roamify.travel.adapters.AutocompleteCustomArrayAdapter;
import com.roamify.travel.adapters.CustomAutoCompleteView;
import com.roamify.travel.adapters.MenuGridRVAdapter;
import com.roamify.travel.listeners.ActivityItemClickListener;
import com.roamify.travel.models.HomePageSearchModel;
import com.roamify.travel.models.MenuItemModel;
import com.roamify.travel.rawdata.RawData;
import com.roamify.travel.utils.Constants;
import com.roamify.travel.utils.Validations;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity implements ActivityItemClickListener, View.OnClickListener {
    static HomePage mInstance;
    protected CustomAutoCompleteView autoCompleteTextView;
    //FrameLayout top_image_portion;
    LinearLayout rv_list_portion;
    RecyclerView mMenuListRecyclerView;
    TextView whereToSearch;
    RelativeLayout rl_autoSearch;


    public static synchronized HomePage getInstance() {
        return mInstance;
    }
    int listViewHeight;
    int totalHeight;
    // adapter for auto-complete
    ArrayAdapter<HomePageSearchModel> myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_home_page);
        initView();

        try {
            Display mDisplay = getWindowManager().getDefaultDisplay();
            listViewHeight = mDisplay.getHeight() - (mDisplay.getHeight() / 3);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        myAdapter = new AutocompleteCustomArrayAdapter(this, R.layout.autocomplete_text_layout, RawData.setHomePageSearchItem());
        autoCompleteTextView.setAdapter(myAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Constants.activityItemClickListener = HomePage.this;
        try {
            mMenuListRecyclerView.setAdapter(new MenuGridRVAdapter(setMenuData(), HomePage.getInstance(), listViewHeight / 4));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClicked(int position) {
        Intent intent = null;
        switch (setMenuData().get(position).getTitle()) {
            case "HOTELS": {
                break;
            }
            case "WATER ACTIVITIES": {
                try {
                    intent = new Intent(getApplicationContext(), DestinationList.class);
                    intent.putExtra("title", "WATER ACTIVITIES");
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            }
            case "AIR ACTIVITIES": {
                try {
                    intent = new Intent(getApplicationContext(), DestinationList.class);
                    intent.putExtra("title", "AIR ACTIVITIES");
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            }
            case "LAND ACTIVITIES": {
                try {
                    intent = new Intent(getApplicationContext(), DestinationList.class);
                    intent.putExtra("title", "LAND ACTIVITIES");
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            }
            case "CONTACT": {
                break;
            }
            case "ABOUT US": {
                break;
            }
            case "FAQ": {
                break;
            }
            case "DESTINATIONS": {
                try {
                    intent = new Intent(getApplicationContext(), DestinationList.class);
                    intent.putExtra("title", "DESTINATIONS");
                    intent.putExtra("isComingForDestinationWiseSearch", true);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            }
        }
    }

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
        title8.setDrawable(R.drawable.faq_button_bg);
        menuItemModels.add(title8);*/

        return menuItemModels;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.textView) {
            Validations.showSoftInput(HomePage.this, autoCompleteTextView);
            autoCompleteTextView.setText("");
            whereToSearch.setVisibility(View.GONE);
            rl_autoSearch.setVisibility(View.VISIBLE);

        }
    }

    private void initView() {
        autoCompleteTextView = (CustomAutoCompleteView) findViewById(R.id.autoCompleteTextView);
        mMenuListRecyclerView = (RecyclerView) findViewById(R.id.rv_menu);
        //top_image_portion = (FrameLayout) findViewById(R.id.ll_top_image_portion);
        rv_list_portion = (LinearLayout) findViewById(R.id.ll_activity_rowLayout);
        whereToSearch = (TextView)findViewById(R.id.textView);
        rl_autoSearch = (RelativeLayout) findViewById(R.id.rl_autoSearch);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mMenuListRecyclerView.setLayoutManager(mLayoutManager);
        mMenuListRecyclerView.setHasFixedSize(true);
        mMenuListRecyclerView.setItemAnimator(new DefaultItemAnimator());

        whereToSearch.setOnClickListener(this);

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    myAdapter.notifyDataSetChanged();
                    myAdapter = new AutocompleteCustomArrayAdapter(HomePage.this, R.layout.autocomplete_text_layout, filter(charSequence.toString()));
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
                whereToSearch.setText(tv_main.getText().toString());
                sendToNext(Integer.parseInt(tv_pos.getText().toString()));
            }
        });
    }

    private ArrayList<HomePageSearchModel> filter(String folderID) {
        final ArrayList<HomePageSearchModel> filteredModelList = new ArrayList<>();
        for (int i = 0; i < RawData.setHomePageSearchItem().size(); i++) {
            HomePageSearchModel model = new HomePageSearchModel();
            final String fId = RawData.setHomePageSearchItem().get(i).getName().toLowerCase();
            if (fId.startsWith(folderID.toLowerCase())) {
                model.setId(RawData.setHomePageSearchItem().get(i).getId());
                model.setName((RawData.setHomePageSearchItem().get(i).getName()));
                model.setType((RawData.setHomePageSearchItem().get(i).getType()));
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    private void sendToNext(int pos){
        Validations.hideSoftInput(HomePage.this);
        try {
            //If item is location type then will go on "All Activites Page"
            //If item is activity type then will go on "Destination List Page"
            if (RawData.setHomePageSearchItem().get(pos).getType().equals("A")) {
                Intent intent = new Intent(getApplicationContext(), DestinationList.class);
                intent.putExtra("title", RawData.setHomePageSearchItem().get(pos).getName());
                intent.putExtra("isComingFromSearchPage", true);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } else {
                Intent intent = new Intent(getApplicationContext(), AllActivities.class);
                intent.putExtra("title", RawData.setHomePageSearchItem().get(pos).getName());
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
