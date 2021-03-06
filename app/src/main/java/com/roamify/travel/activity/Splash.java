package com.roamify.travel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.roamify.travel.R;
import com.roamify.travel.utils.AppController;
import com.roamify.travel.utils.Validations;

public class Splash extends AppCompatActivity {
    public static int SPLASH_DISPLAY_TIME = 2000; /*2 seconds*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                try {
                    /*if (Validations.isNotNullNotEmptyNotWhiteSpace(AppController.getInstance().getUserId())) {
                        intent = new Intent(Splash.this, HomePageWithMenu.class);
                    } else {
                        intent = new Intent(Splash.this, HomePageWithMenu.class);
                    }*/

                    if (AccessToken.getCurrentAccessToken() == null) {
                        intent = new Intent(Splash.this, LoginActivity.class);
                    } else {
                        intent = new Intent(Splash.this, HomePageWithMenu.class);
                    }

                    intent.putExtra("isComingFromSplash", true);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }, SPLASH_DISPLAY_TIME);
    }

}
