package com.roamify.travel.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.roamify.travel.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    protected Toolbar toolbar;
    protected AppBarLayout appbar;
    protected EditText etUsername;
    protected EditText etPassword;
    protected Button btSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_login);
        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bt_signIn) {

        }
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btSignIn = (Button) findViewById(R.id.bt_signIn);
        btSignIn.setOnClickListener(LoginActivity.this);
    }
}
