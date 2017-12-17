package com.roamify.travel.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.roamify.travel.R;
import com.roamify.travel.adapters.ImagePagerAdapter;
import com.roamify.travel.dialogs.AlertDialogManager;
import com.roamify.travel.fragment.DescriptionFragment;
import com.roamify.travel.fragment.LocationFragment;
import com.roamify.travel.fragment.ReviewsFragment;
import com.roamify.travel.models.PackageDetailsModel;
import com.roamify.travel.models.PackageTabModel;
import com.roamify.travel.rawdata.RawData;
import com.roamify.travel.utils.AppController;
import com.roamify.travel.utils.CheckConnection;
import com.roamify.travel.utils.Constants;
import com.roamify.travel.utils.Validations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityPackageDetails extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    protected Toolbar toolbar;
    protected ViewPager treeDetailPager;
    protected TextView tvPackagename;
    protected TextView tvPackagesubmit;
    protected TabLayout tablayout;
    protected LinearLayout tabview;
    protected LinearLayout detailsViewContainer;
    LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;
    ImagePagerAdapter mAdapter;
    private String[] mImages;
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    String request_tag = "get_package_details";
    String request_tag_for_deal = "get_deal";
    public PackageDetailsModel packageDetailsModel;
    static ActivityPackageDetails mInstance;

    public static synchronized ActivityPackageDetails getInstance() {
        return mInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_package_details);
        initView();
        mInstance = this;
        findViewById(R.id.right_bar_button).setOnClickListener(this);
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        setTabName(RawData.setPackageTabMenu());
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        tablayout.setScrollPosition(0, 0f, true);

        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                final String tId = RawData.setPackageTabMenu().get(tab.getPosition()).getTabId();
                final String tName = RawData.setPackageTabMenu().get(tab.getPosition()).getTabName();
                try {
                    switch (tName) {
                        case "Details":
                            fragment = new DescriptionFragment();
                            transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.details_view_container, fragment).addToBackStack(null).commit();
                            break;
                        /*case "Package Details":
                            fragment = new PackageDetailsFragment();
                            transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.details_view_container, fragment).addToBackStack(null).commit();
                            break;*/
                        case "Location":
                            fragment = new LocationFragment();
                            transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.details_view_container, fragment).addToBackStack(null).commit();
                            break;
                        case "Reviews":
                            fragment = new ReviewsFragment();
                            transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.details_view_container, fragment).addToBackStack(null).commit();
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (new CheckConnection(getApplicationContext()).isConnectedToInternet()) {
            String URL = Constants.BaseUrl + "getPackageDetailsByPackage.php?packageId=" + getIntent().getStringExtra("package_id");
            try {
                getRequestCall(URL, request_tag, null);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            AlertDialogManager.showAlartDialog(ActivityPackageDetails.this, getString(R.string.no_network_title), getString(R.string.no_network_msg));
        }
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        treeDetailPager = (ViewPager) findViewById(R.id.tree_detail_pager);
        tvPackagename = (TextView) findViewById(R.id.tv_packagename);
        tvPackagesubmit = (TextView) findViewById(R.id.tv_packagesubmit);
        tvPackagesubmit.setOnClickListener(ActivityPackageDetails.this);
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        tabview = (LinearLayout) findViewById(R.id.tabview);
        detailsViewContainer = (LinearLayout) findViewById(R.id.details_view_container);
        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Package Details");
        toolbar.setTitleTextAppearance(this, R.style.NavBarTitle);
        toolbar.setSubtitleTextAppearance(this, R.style.NavBarSubTitle);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException npe) {
            npe.getMessage();
        }

        try {
            final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
            upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validations.hideSoftInput(ActivityPackageDetails.this);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_packagesubmit) {
            showQueryDialog();
        }else if (view.getId() == R.id.right_bar_button) {
            Intent intent = new Intent(getApplicationContext(), HomePageWithMenu.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
        }
    }

    private void setTabName(List<PackageTabModel> folderList) {
        tablayout.removeAllTabs();
        tablayout.newTab();
        if (folderList.size() > 0) {
            for (int i = 0; i < folderList.size(); i++) {
                PackageTabModel packageTabModel = folderList.get(i);
                tablayout.addTab(tablayout.newTab().setText(packageTabModel.getTabName()));
            }
        }
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    private class ImagePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImages.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            Context context = getApplicationContext();
            ImageView imageView = new ImageView(context);
            //imageView.setBackgroundColor(context.getResources().getColor(R.color.black));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            try {
                String imagePath = mImages[position];
                try {
                    Glide.with(getApplicationContext())
                            .load(Constants.BaseImageUrl + imagePath)
                            //.fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .crossFade(1000)
                            .override(getScreenWidth(getApplicationContext()), getScreenWidth(getApplicationContext())/4)
                            .error(R.drawable.no_image_found)
                            .placeholder(R.drawable.no_image_found)
                            .into(imageView);
                } catch (Exception e) {
                    e.fillInStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //imageRequest(getActivity(), new Variables().getImageBaseUrl() + imagePath, imageView);
            container.addView(imageView, 0);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView) object);
        }

        public CharSequence getPageTitle(int position) {
            return "Your static title";
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onPageSelected(int position) {
        // TODO Auto-generated method stub
        Log.d("###onPageSelected, pos ", String.valueOf(position));
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselected_itemdots));
        }
        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dots));
    }

    private void setUiPageViewController() {

        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getApplicationContext());
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselected_itemdots));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dots));
    }

    public void showQueryDialog() {
        try {
            final Dialog mdialog = new Dialog(ActivityPackageDetails.this);
            mdialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            mdialog.setContentView(R.layout.query_submition_dialog);
            mdialog.setCancelable(false);
            mdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Window window = mdialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            window.setAttributes(wlp);

            final EditText nameEditText = (EditText) mdialog.findViewById(R.id.name);
            final EditText emailEditText = (EditText) mdialog.findViewById(R.id.email);
            final EditText phoneEditText = (EditText) mdialog.findViewById(R.id.phone);
            final EditText commentEditText = (EditText) mdialog.findViewById(R.id.comment);

            TextView okButton = (TextView) mdialog.findViewById(R.id.submitButton);
            TextView cancelButton = (TextView) mdialog.findViewById(R.id.cancelButton);

            okButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    mdialog.dismiss();
                    /*JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("packageId", packageDetailsModel.getId());
                        jsonObject.put("userName", nameEditText);
                        jsonObject.put("userEmail", emailEditText);
                        jsonObject.put("userPhone", phoneEditText);
                        jsonObject.put("comment", commentEditText);
                        jsonObject.put("source", packageDetailsModel.getSource());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/

                    if (new CheckConnection(getApplicationContext()).isConnectedToInternet()) {
                        try {
                            String name = nameEditText.getText().toString().trim();
                            String email = emailEditText.getText().toString().trim();
                            String phone = phoneEditText.getText().toString().trim();
                            String comment = commentEditText.getText().toString().trim();

                            if(!Validations.isNotNullNotEmptyNotWhiteSpace(email)) {
                                AlertDialogManager.showAlartDialog(ActivityPackageDetails.this, "Alert!", "Email can not be blank.");
                                return;
                            }

                            if (!Validations.emailValidator(email)) {
                                AlertDialogManager.showAlartDialog(ActivityPackageDetails.this, "Alert!", "Please enter valid email address.");
                                return;
                            }

                            if(!Validations.isNotNullNotEmptyNotWhiteSpace(phone)) {
                                AlertDialogManager.showAlartDialog(ActivityPackageDetails.this, "Alert!", "Phone can not be blank.");
                                return;
                            }

                            String Url = Constants.BaseUrl + "sendPackageDeal.php?packageId=" + packageDetailsModel.getId() + "&userName=" + name + "&userEmail=" + email + "&userPhone=" + phone + "&comment=" + comment + "&source=" + packageDetailsModel.getSource();
                            getRequestCall(Url, request_tag_for_deal, null);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        AlertDialogManager.showAlartDialog(ActivityPackageDetails.this, getString(R.string.no_network_title), getString(R.string.no_network_msg));
                    }
                }
            });
            cancelButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    mdialog.dismiss();
                }
            });
            mdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getRequestCall(String url, String tag, JSONObject jsonObject) {
        // cancel request from pending queue
        AppController.getInstance().cancelPendingRequests(tag);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, jsonObject,
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
        if (response.has("details")) {
            JSONArray jsonArray = response.getJSONArray("details");
            PackageDetailsModel model = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                model = new PackageDetailsModel();
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String id = jsonObject.getString("id");
                String locationId = jsonObject.getString("locationId");
                String activityId = jsonObject.getString("activityId");
                String packageName = jsonObject.getString("packageName");
                String address = jsonObject.getString("address");
                String packagePrice = jsonObject.getString("packagePrice");
                String description = jsonObject.getString("description");
                String features = jsonObject.getString("features");
                String specification = jsonObject.getString("specification");
                String ratings = jsonObject.getString("ratings");
                String source = jsonObject.getString("source");
                String duration = jsonObject.getString("duration");
                String galleryImages = jsonObject.getString("galleryImages");
                String[] spilltedImages = galleryImages.split(",");

                mImages=spilltedImages;
                model.setId(id);
                model.setLocationId(locationId);
                model.setActivityId(activityId);
                model.setPackageName(packageName);
                model.setAddress(address);
                model.setPackagePrice(packagePrice);
                model.setDescription(description);
                model.setFeatures(features);
                model.setSpecification(specification);
                model.setRatings(ratings);
                model.setSource(source);
                model.setDuration(duration);
                model.setGalleryImages(spilltedImages);
            }
            displayPackageDetails(model);
        } else {
            String status = response.getString("status");
            if (status.equals("1"))
                AlertDialogManager.showAlartDialog(ActivityPackageDetails.this, "Thank You!", "Thank you, our team will contact you shortly.");
            else
                AlertDialogManager.showAlartDialog(ActivityPackageDetails.this, "Alert!", "There is some problem, please try again.");

            //Log.d("Submit Res", "My Res: " + response);
        }
    }

    private void displayPackageDetails(PackageDetailsModel packageDetailsModel) {
        if (packageDetailsModel != null) {
            this.packageDetailsModel = packageDetailsModel;
            //mImages = new String[packageDetailsModel.getGalleryImages().length];
            try {
                //mAdapter = new ImagePagerAdapter(ActivityPackageDetails.this, mImages);
                mAdapter = new ImagePagerAdapter();
                treeDetailPager.setAdapter(mAdapter);
                treeDetailPager.setCurrentItem(0);
                treeDetailPager.setOnPageChangeListener(this);
                setUiPageViewController();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            tvPackagename.setText(packageDetailsModel.getPackageName());

            fragment = new DescriptionFragment();
            transaction.replace(R.id.details_view_container, fragment).addToBackStack(null).commit();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Constants.activityItemClickListener = null;
    }

    public int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }


    public int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
