package com.roamify.travel.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.roamify.travel.R;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    protected Toolbar toolbar;
    protected AppBarLayout appbar;
    protected EditText etName;
    protected EditText etPhone;
    protected EditText etZipcode;
    protected EditText etUsername;
    protected EditText etPassword;
    protected Button btSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_registration);
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
        etName = (EditText) findViewById(R.id.et_name);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etZipcode = (EditText) findViewById(R.id.et_zipcode);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btSignUp = (Button) findViewById(R.id.bt_signIn);
        btSignUp.setOnClickListener(RegistrationActivity.this);
    }
}
