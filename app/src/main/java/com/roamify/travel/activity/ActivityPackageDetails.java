package com.roamify.travel.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.roamify.travel.R;
import com.roamify.travel.fragment.DescriptionFragment;
import com.roamify.travel.fragment.LocationFragment;
import com.roamify.travel.fragment.PackageDetailsFragment;
import com.roamify.travel.fragment.TestimonialsFragment;
import com.roamify.travel.models.PackageDetailsModel;
import com.roamify.travel.models.PackageTabModel;
import com.roamify.travel.rawdata.RawData;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ActivityPackageDetails extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    protected Toolbar toolbar;
    protected ViewPager treeDetailPager;
    protected TextView tvPackagename;
    protected TextView tvPackagesubmit;
    protected TabLayout tablayout;
    protected LinearLayout tabview;
    protected LinearLayout detailsViewContainer;
    ArrayList<PackageDetailsModel> detailsModelArrayList = new ArrayList<>();
    LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;
    ImagePagerAdapter mAdapter;
    private int[] mImages;
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_package_details);
        initView();
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        setTabName(RawData.setPackageTabMenu());
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        fragment = new DescriptionFragment();
        transaction.replace(R.id.details_view_container, fragment).addToBackStack(null).commit();
        tablayout.setScrollPosition(0, 0f, true);

        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                final String tId = RawData.setPackageTabMenu().get(tab.getPosition()).getTabId();
                final String tName = RawData.setPackageTabMenu().get(tab.getPosition()).getTabName();
                try {
                    switch (tName)
                    {
                        case "Description":
                            fragment = new DescriptionFragment();
                            transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.details_view_container, fragment).addToBackStack(null).commit();
                            break;
                        case "Package Details":
                            fragment = new PackageDetailsFragment();
                            transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.details_view_container, fragment).addToBackStack(null).commit();
                            break;
                        case "Map":
                            fragment = new LocationFragment();
                            transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.details_view_container, fragment).addToBackStack(null).commit();
                            break;
                        case "Testimonials":
                            fragment = new TestimonialsFragment();
                            transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.details_view_container, fragment).addToBackStack(null).commit();
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //transaction.replace(R.id.details_view_container, fragment).addToBackStack(null).commitNow();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        detailsModelArrayList = RawData.setPackageDetails();

        if (detailsModelArrayList.size() > 0) {
            PackageDetailsModel detailsModel = detailsModelArrayList.get(0);
            try {
                JSONArray jsonArray = detailsModel.getPackageImages().getJSONArray("ImageArray");
                mImages = new int[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    int imgPath = jsonArray.getInt(i);
                    mImages[i] = imgPath;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                mAdapter = new ImagePagerAdapter();
                treeDetailPager.setAdapter(mAdapter);
                treeDetailPager.setCurrentItem(0);
                treeDetailPager.setOnPageChangeListener(this);
                setUiPageViewController();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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
        toolbar.setTitle("");
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
        }
        catch (RuntimeException re)
        {
            re.printStackTrace();
        }catch (Exception e)
        {
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
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_packagesubmit) {
            showQueryDialog();
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
            imageView.setBackgroundColor(context.getResources().getColor(R.color.black));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //Just for testing

            try {
                int imagePath = mImages[position];
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imagePath);
                imageView.setImageBitmap(bitmap);
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
            TextView okButton = (TextView) mdialog.findViewById(R.id.submitButton);
            TextView cancelButton = (TextView) mdialog.findViewById(R.id.cancelButton);

            /*TextView title = (TextView) mdialog.findViewById(R.id.title);
            TextView message = (TextView) mdialog.findViewById(R.id.msg);
            message.setText(msg);*/

            okButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    mdialog.dismiss();
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
}
