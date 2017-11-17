package com.roamify.travel.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.appcompat.BuildConfig;
import android.util.Log;

public class CheckConnection {
    private static final String WEB_ACCESS_DEBUG_TAG = "WEBACCESSLIB";
    private final String USER_AGENT;
    private Context context;
    private boolean mobileConnected;
    private boolean wifiConnected;

    public CheckConnection(Context context) {
        this.USER_AGENT = "Mozilla/5.0";
        this.context = context;
        this.wifiConnected = false;
        this.mobileConnected = false;
    }

    public boolean getWifiConnected() {
        return this.wifiConnected;
    }

    public boolean getMobileConnected() {
        return this.mobileConnected;
    }

    public boolean isConnectedToInternet() {
        NetworkInfo networkInfo = ((ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            this.wifiConnected = false;
            this.mobileConnected = false;
            if (BuildConfig.DEBUG) {
                Log.d(WEB_ACCESS_DEBUG_TAG, "NOT Connected with Network");
            }
        } else {
            boolean z;
            this.wifiConnected = networkInfo.getType() == 1;
            if (networkInfo.getType() == 0) {
                z = true;
            } else {
                z = false;
            }
            this.mobileConnected = z;
            if (BuildConfig.DEBUG) {
                Log.d(WEB_ACCESS_DEBUG_TAG, "Connected to Network");
            }
        }
        if (this.wifiConnected || this.mobileConnected) {
            return true;
        }
        return false;
    }
}
