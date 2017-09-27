package com.roamify.travel.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roamify.travel.R;
import com.roamify.travel.activity.MenuPage;
import com.roamify.travel.models.MenuItemModel;

import java.util.ArrayList;

/**
 * Created by kapilyadav on 27-Sep-17.
 */

public class MenuGridRVAdapter extends RecyclerView.Adapter<MenuViewHandler> implements View.OnClickListener {
    private MenuItemModel menuItemModel = new MenuItemModel();
    private Activity activity;

    public MenuGridRVAdapter(ArrayList<MenuItemModel> menuItemModels, Activity activity) {
        this.activity = activity;
        menuItemModel.setMenuItemModels(menuItemModels);
    }

    @Override
    public MenuViewHandler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_items, parent, false);
        return new MenuViewHandler(itemView);
    }

    @Override
    public void onBindViewHolder(MenuViewHandler holder, int position) {
        MenuItemModel data = menuItemModel.getMenuItemModels().get(position);
        menuItemModel.setHolder(holder);
        menuItemModel.setPosition(position);
        if (data != null) {
            try {
                holder.tv_Title.setText(data.getTitle());
                holder.ll_rowLayout.setBackgroundResource(data.getDrawable());
            } catch (Exception e) {
                e.getMessage();
            }
            holder.ll_rowLayout.setOnClickListener(this);
        }
    }

    @Override
    public int getItemCount() {
        return menuItemModel.getMenuItemModels().size();
    }

    @Override
    public void onClick(View view) {
        if (view == menuItemModel.getHolder().ll_rowLayout) {
            MenuPage.getInstance().getMenuItemClickListener().menuClicked(menuItemModel.getPosition());
        }
    }
}
