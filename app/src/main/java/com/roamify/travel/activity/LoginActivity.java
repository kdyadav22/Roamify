package com.roamify.travel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.roamify.travel.R;
import com.roamify.travel.utils.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    protected Toolbar toolbar;
    protected AppBarLayout appbar;
    protected EditText etUsername;
    protected EditText etPassword;
    protected Button btSignIn;
    protected Button btSkip;
    protected TextView registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_login);
        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bt_signIn) {
            try {
                Intent intent = new Intent(getApplicationContext(), HomePageWithMenu.class);
                if (getIntent().getBooleanExtra("comingFromHomePage", false))
                    intent.putExtra("comingFromHomePage", true);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (view.getId() == R.id.registerButton) {
            try {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                if (getIntent().getBooleanExtra("comingFromHomePage", false))
                    intent.putExtra("comingFromHomePage", true);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (view.getId() == R.id.bt_skip) {
            try {
                AppController.getInstance().isSkipped(true);
                Intent intent = new Intent(getApplicationContext(), HomePageWithMenu.class);
                if (getIntent().getBooleanExtra("comingFromHomePage", false))
                    intent.putExtra("comingFromHomePage", true);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btSignIn = (Button) findViewById(R.id.bt_signIn);
        btSignIn.setOnClickListener(LoginActivity.this);
        btSkip = (Button) findViewById(R.id.bt_skip);
        btSkip.setOnClickListener(LoginActivity.this);
        registerButton = (TextView) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(LoginActivity.this);
    }

    public void getRequestCall(String url, String tag) {
        // cancel request from pending queue
        AppController.getInstance().cancelPendingRequests(tag);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
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

    }
}
