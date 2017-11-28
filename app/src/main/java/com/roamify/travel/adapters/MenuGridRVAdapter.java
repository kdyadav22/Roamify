package com.roamify.travel.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roamify.travel.R;
import com.roamify.travel.activity.HomePage;
import com.roamify.travel.models.MenuItemModel;
import com.roamify.travel.utils.Constants;

import java.util.ArrayList;

/**
 * Created by kapilyadav on 27-Sep-17.
 */

public class MenuGridRVAdapter extends RecyclerView.Adapter<MenuViewHandler> {
    private MenuItemModel menuItemModel = new MenuItemModel();
    private Activity activity;
    int menuHeight;

    public MenuGridRVAdapter(ArrayList<MenuItemModel> menuItemModels, Activity activity, int menuHeight) {
        this.activity = activity;
        menuItemModel.setMenuItemModels(menuItemModels);
        this.menuHeight = menuHeight;
    }

    @Override
    public MenuViewHandler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_items, parent, false);
        return new MenuViewHandler(itemView);
    }

    @Override
    public void onBindViewHolder(MenuViewHandler holder, final int position) {
        MenuItemModel data = menuItemModel.getMenuItemModels().get(position);
        menuItemModel.setHolder(holder);
        menuItemModel.setPosition(position);
        if (data != null) {
            try {
                holder.tv_Title.setText(data.getTitle());
                /*if (menuHeight > 0)
                    holder.ll_rowLayout.getLayoutParams().height = menuHeight;*/
               // holder.ll_rowLayout.setBackgroundResource(data.getDrawable());
            } catch (Exception e) {
                e.getMessage();
            }
            holder.ll_rowLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Constants.activityItemClickListener != null)
                        Constants.activityItemClickListener.onClicked(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return menuItemModel.getMenuItemModels().size();
    }

}
