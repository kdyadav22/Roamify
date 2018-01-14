package com.roamify.travel.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.io.File;


public class AppController extends Application {
    public static final String TAG = AppController.class.getSimpleName();
    private SharedPreferences mSharedPreferences, mAppSharedPreferences;
    private static AppController mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private Context ctx;
    public Context getCtx() {
        return ctx;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        mAppSharedPreferences = getSharedPreferences("mApp", MODE_PRIVATE);
        mInstance = this;
        enableStrictMode();
        ctx = this;
    }

    @Override
    public void onLowMemory() {
        Log.d("On Low Memory", "Low memory detected");
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        Log.d("On Terminate", "onTerminate");
        super.onTerminate();
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public void logout() {
        mSharedPreferences.edit().clear().apply();
        /*// After logout redirect user to Loing Activity
        Intent i = new Intent(getApplicationContext(), HomePageWithMenu.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Staring Login Activity
        startActivity(i);*/
        deleteCache(getApplicationContext());
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }
        return dir != null && dir.delete();
    }

    public boolean getSkipped() {
        return mSharedPreferences.getBoolean("skipped", false);
    }

    public void isSkipped(boolean skipped) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean("skipped", skipped);
        editor.apply();
    }

    public String getUserId() {
        return mSharedPreferences.getString("userid", null);
    }

    public void setUserId(String userid) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("userid", userid);
        editor.apply();
    }

    public String getSearchText() {
        return mAppSharedPreferences.getString("searchText", "Search for?");
    }

    public void setSearchText(String searchedText) {
        SharedPreferences.Editor editor = mAppSharedPreferences.edit();
        editor.putString("searchText", searchedText);
        editor.apply();
    }

    public void setSearchImage(String treeImage) {
        SharedPreferences.Editor editor = mAppSharedPreferences.edit();
        editor.putString("searchImage", treeImage);
        editor.apply();
    }

    public String getSearchImage() {
        return mAppSharedPreferences.getString("searchImage", null);
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {

            mRequestQueue = Volley.newRequestQueue(getApplicationContext(),
                    new HurlStack());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        req.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public void showGenericToast(String error) {
        try {
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @SuppressLint("NewApi")
    private static void enableStrictMode() {
        // strict mode requires API level 9 or later
        if (Build.VERSION.SDK_INT > 22)
            return;

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .penaltyFlashScreen()
                .build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectActivityLeaks()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .detectLeakedRegistrationObjects()
                .penaltyLog()
                .build());

        Log.w("Strict Mode", "Strict mode enabled");
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("isMyServiceRunning?", true + "");
                return true;
            }
        }
        Log.i("isMyServiceRunning?", false + "");
        return false;
    }
}