package com.roamify.travel.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.roamify.travel.R;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener {

    protected ImageView rightBarButton;
    protected Toolbar toolbar;
    protected AppBarLayout appbar;
    protected ImageView logoImageview;
    protected TextView phoneNumber;
    protected TextView supportUrl;
    protected TextView websiteUrl;
    protected LinearLayout tavLayout;
    protected ImageView rightBarSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_contact_us);
        initView();
        rightBarSearchButton.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.right_bar_button) {
            Intent intent = new Intent(getApplicationContext(), HomePageWithMenu.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
        }
    }

    private void initView() {
        rightBarButton = (ImageView) findViewById(R.id.right_bar_button);
        rightBarButton.setOnClickListener(ContactUsActivity.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        logoImageview = (ImageView) findViewById(R.id.logo_imageview);
        phoneNumber = (TextView) findViewById(R.id.phone_number);
        supportUrl = (TextView) findViewById(R.id.support_url);
        websiteUrl = (TextView) findViewById(R.id.website_url);
        tavLayout = (LinearLayout) findViewById(R.id.tavLayout);

        toolbar.setTitle(getIntent().getStringExtra("title"));
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
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });
        rightBarSearchButton = (ImageView) findViewById(R.id.right_bar_search_button);
    }
}
