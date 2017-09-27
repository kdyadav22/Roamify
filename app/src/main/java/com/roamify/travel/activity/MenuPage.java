package com.roamify.travel.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.roamify.travel.R;
import com.roamify.travel.adapters.MenuGridRVAdapter;
import com.roamify.travel.listeners.MenuItemClickListener;
import com.roamify.travel.models.MenuItemModel;

import java.util.ArrayList;

public class MenuPage extends AppCompatActivity implements MenuItemClickListener {

    public MenuItemClickListener getMenuItemClickListener() {
        return menuItemClickListener;
    }

    private void setMenuItemClickListener(MenuItemClickListener menuItemClickListener) {
        this.menuItemClickListener = menuItemClickListener;
    }

    private MenuItemClickListener menuItemClickListener;
    static MenuPage mInstance;
    private RecyclerView mMenuListRecyclerView;

    public static synchronized MenuPage getInstance() {
        return mInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);
        setMenuItemClickListener(this);
        mMenuListRecyclerView = (RecyclerView) findViewById(R.id.rv_menu);
        GridLayoutManager mLayoutManager2 = new GridLayoutManager(getApplicationContext(), 2);
        mMenuListRecyclerView.setLayoutManager(mLayoutManager2);
        mMenuListRecyclerView.setHasFixedSize(true);
        mMenuListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mMenuListRecyclerView.setAdapter(new MenuGridRVAdapter(setMenuData(), MenuPage.getInstance()));

    }

    @Override
    public void menuClicked(int position) {
        Toast.makeText(getApplicationContext(), "Clciked", Toast.LENGTH_LONG).show();
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
        title2.setTitle(getResources().getString(R.string.air_activity));
        title2.setDrawable(R.drawable.air_activity_button_bg);
        menuItemModels.add(title2);

        //Item3
        MenuItemModel title3 = new MenuItemModel();
        title3.setTitle(getResources().getString(R.string.water_activity));
        title3.setDrawable(R.drawable.water_activity_button_bg);
        menuItemModels.add(title3);

        //Item4
        MenuItemModel title4 = new MenuItemModel();
        title4.setTitle(getResources().getString(R.string.land_activity));
        title4.setDrawable(R.drawable.hotels_button_bg);
        menuItemModels.add(title4);

        //Item5
        MenuItemModel title5 = new MenuItemModel();
        title5.setTitle(getResources().getString(R.string.destination));
        title5.setDrawable(R.drawable.air_activity_button_bg);
        menuItemModels.add(title5);

        //Item6
        MenuItemModel title6 = new MenuItemModel();
        title6.setTitle(getResources().getString(R.string.contact_us));
        title6.setDrawable(R.drawable.water_activity_button_bg);
        menuItemModels.add(title6);

        return menuItemModels;
    }
}
