package com.roamify.travel.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.roamify.travel.R;
import com.roamify.travel.utils.Constants;

import java.util.ArrayList;

/**
 * Created by Panalink-03 on 11/20/2017.
 */


public class ImagePagerAdapter extends PagerAdapter {
    String[] mImages;
    Activity activity;

    public ImagePagerAdapter(Activity activity, String[] mImages) {
        this.activity = activity;
        this.mImages = mImages;
    }

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
        Context context = activity;
        String imagePath = mImages[position];
        ImageView imageView;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.viewpager_image_item, container, false);
        imageView = (ImageView) viewLayout.findViewById(R.id.imgDisplay);
        imageView.setBackgroundColor(context.getResources().getColor(R.color.black));

            try {
                Glide.with(activity)
                        .load(Constants.BaseImageUrl + imagePath)
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .crossFade(1000)
                        //.override(600, 400)
                        .error(R.drawable.no_image_found)
                        .placeholder(R.drawable.no_image_found)
                        .into(imageView);
            } catch (Exception e) {
                e.fillInStackTrace();
            }

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
