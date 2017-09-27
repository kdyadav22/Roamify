package com.roamify.travel.models;

import com.roamify.travel.adapters.MenuViewHandler;

import java.util.ArrayList;

/**
 * Created by kapilyadav on 27-Sep-17.
 */

public class MenuItemModel {
    public ArrayList<MenuItemModel> getMenuItemModels() {
        return menuItemModels;
    }

    public void setMenuItemModels(ArrayList<MenuItemModel> menuItemModels) {
        this.menuItemModels = menuItemModels;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    ArrayList<MenuItemModel> menuItemModels;
    String title;

    public MenuViewHandler getHolder() {
        return holder;
    }

    public void setHolder(MenuViewHandler holder) {
        this.holder = holder;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    MenuViewHandler holder;
    int position;

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    int drawable;

}
