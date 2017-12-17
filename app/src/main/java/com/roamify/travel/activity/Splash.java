package com.roamify.travel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.roamify.travel.R;
import com.roamify.travel.utils.AppController;

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
                    if(AppController.getInstance().getSkipped()) {
                        intent = new Intent(Splash.this, HomePageWithMenu.class);
                    }
                    else{
                        intent = new Intent(Splash.this, HomePageWithMenu.class);
                    }

                    finish();
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    startActivity(intent);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        },SPLASH_DISPLAY_TIME);
    }

}
