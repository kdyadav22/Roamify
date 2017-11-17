package com.roamify.travel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.widget.LinearLayout;

import com.roamify.travel.R;
import com.roamify.travel.adapters.MenuGridRVAdapter;
import com.roamify.travel.listeners.MenuItemClickListener;
import com.roamify.travel.models.MenuItemModel;

import java.util.ArrayList;

public class MenuPage extends AppCompatActivity implements MenuItemClickListener {

    public static MenuItemClickListener menuItemClickListener;
    static MenuPage mInstance;
    LinearLayout top_image_portion, rv_list_portion;

    public static synchronized MenuPage getInstance() {
        return mInstance;
    }

    int listViewHeight;
    int totalHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);
        menuItemClickListener = this;
        RecyclerView mMenuListRecyclerView = (RecyclerView) findViewById(R.id.rv_menu);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mMenuListRecyclerView.setLayoutManager(mLayoutManager);
        mMenuListRecyclerView.setHasFixedSize(true);
        mMenuListRecyclerView.setItemAnimator(new DefaultItemAnimator());

        top_image_portion = (LinearLayout) findViewById(R.id.ll_top_image_portion);
        rv_list_portion = (LinearLayout) findViewById(R.id.ll_activity_rowLayout);

        try {
            Display mDisplay = getWindowManager().getDefaultDisplay();
            listViewHeight = mDisplay.getHeight() - (mDisplay.getHeight() / 3);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            mMenuListRecyclerView.setAdapter(new MenuGridRVAdapter(setMenuData(), MenuPage.getInstance(), listViewHeight / 4));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void menuClicked(int position) {
        Intent intent = null;
        switch (setMenuData().get(position).getTitle()) {
            case "HOTELS": {
                break;
            }
            case "WATER ACTIVITIES": {
                try {
                    intent = new Intent(getApplicationContext(), ActivityDetails.class);
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
                    intent = new Intent(getApplicationContext(), ActivityDetails.class);
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
                    intent = new Intent(getApplicationContext(), ActivityDetails.class);
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
                    intent = new Intent(getApplicationContext(), ActivitiesList.class);
                    intent.putExtra("title", "DESTINATIONS");
                    startActivity(intent);
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
        MenuItemModel title1 = new MenuItemModel();
        title1.setTitle(getResources().getString(R.string.hotels));
        title1.setDrawable(R.drawable.hotels_button_bg);
        menuItemModels.add(title1);

        //Item2
        MenuItemModel title2 = new MenuItemModel();
        title2.setTitle(getResources().getString(R.string.water_activity));
        title2.setDrawable(R.drawable.water_activity_button_bg);
        menuItemModels.add(title2);

        //Item3
        MenuItemModel title3 = new MenuItemModel();
        title3.setTitle(getResources().getString(R.string.air_activity));
        title3.setDrawable(R.drawable.air_activity_button_bg);
        menuItemModels.add(title3);

        //Item4
        MenuItemModel title4 = new MenuItemModel();
        title4.setTitle(getResources().getString(R.string.land_activity));
        title4.setDrawable(R.drawable.land_activity_button_bg);
        menuItemModels.add(title4);

        //Item5
        MenuItemModel title5 = new MenuItemModel();
        title5.setTitle(getResources().getString(R.string.destination));
        title5.setDrawable(R.drawable.destination_button_bg);
        menuItemModels.add(title5);

        //Item6
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
        menuItemModels.add(title8);

        return menuItemModels;
    }
}
