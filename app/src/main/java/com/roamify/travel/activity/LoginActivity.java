package com.roamify.travel.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.roamify.travel.R;
import com.roamify.travel.dialogs.AlertDialogManager;
import com.roamify.travel.facebook.FaceBookLogin;
import com.roamify.travel.googleplus.GooglePlusLogin;
import com.roamify.travel.utils.AppController;
import com.roamify.travel.utils.CheckConnection;
import com.roamify.travel.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, FaceBookLogin.MFacebookCallback, GooglePlusLogin.GooglePlusCallback {

    protected Toolbar toolbar;
    protected AppBarLayout appbar;
    protected EditText etUsername;
    protected EditText etPassword;
    protected TextView btSkipTextView;
    protected TextView registerButton;
    private static final String TAG = LoginActivity.class.getSimpleName();
    protected ImageView loginButton;
    private SignInButton btnSignIn;
    private CallbackManager mCallbackManager;
    FaceBookLogin faceBookLogin;
    GooglePlusLogin googlePlusLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_login);
        initView();
        //Facebook Login initialization
        mCallbackManager = CallbackManager.Factory.create();
        faceBookLogin = new FaceBookLogin(LoginActivity.this, mCallbackManager);
        //End

        //Custom call
        //Google + initialization
        googlePlusLogin = new GooglePlusLogin(LoginActivity.this);
        googlePlusLogin.initializeGooglePlus();
        //End

        //printKeyHash(LoginActivity.this);
    }

    private void nextActivity(Profile profile) {
        if (profile != null) {
            if (getIntent().getBooleanExtra("isComingFromDetails", false)) {
                onBackPressed();
            } else {
                Intent main = new Intent(LoginActivity.this, HomePageWithMenu.class);
                main.putExtra("name", profile.getFirstName());
                main.putExtra("surname", profile.getLastName());
                main.putExtra("imageUrl", profile.getProfilePictureUri(200, 200).toString());
                startActivity(main);
                finish();
            }

        }
    }

    protected void getUserDetails(LoginResult loginResult) {
        GraphRequest data_request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject json_object,
                            GraphResponse response) {
                        Intent intent = new Intent(LoginActivity.this,
                                HomePageWithMenu.class);
                        intent.putExtra("userProfile", json_object.toString());
                        startActivity(intent);
                    }

                });
        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "id,name,email,picture.width(120).height(120)");
        data_request.setParameters(permission_param);
        data_request.executeAsync();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_sign_in) {
            try {
                if (new CheckConnection(getApplicationContext()).isConnectedToInternet()) {
                    //Custom call
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                googlePlusLogin.showProgressDialog();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });

                    googlePlusLogin.signIn();
                } else {
                    AlertDialogManager.showAlartDialog(LoginActivity.this, getString(R.string.no_network_title), getString(R.string.no_network_msg));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (view.getId() == R.id.tv_skipBtn) {
            if (new CheckConnection(getApplicationContext()).isConnectedToInternet()) {
                AppController.getInstance().isSkipped(true);
                goToNext();
            } else {
                AlertDialogManager.showAlartDialog(LoginActivity.this, getString(R.string.no_network_title), getString(R.string.no_network_msg));
            }
        } else if (view.getId() == R.id.login_button) {
            if (new CheckConnection(getApplicationContext()).isConnectedToInternet())
                faceBookLogin.onFblogin();
            else
                AlertDialogManager.showAlartDialog(LoginActivity.this, getString(R.string.no_network_title), getString(R.string.no_network_msg));
        }
    }

    private void goToNext() {
        try {
            if (getIntent().getBooleanExtra("isComingFromSplash", false)) {
                Intent intent = new Intent(getApplicationContext(), HomePageWithMenu.class);
                if (getIntent().getBooleanExtra("comingFromHomePage", false))
                    intent.putExtra("comingFromHomePage", true);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } else {
                finish();
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btSkipTextView = (TextView) findViewById(R.id.tv_skipBtn);
        btSkipTextView.setOnClickListener(LoginActivity.this);
        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
        btnSignIn.setOnClickListener(this);
        loginButton = (ImageView) findViewById(R.id.login_button);
        loginButton.setOnClickListener(LoginActivity.this);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);

        //Custom call
        if (requestCode == googlePlusLogin.mResultCode()) {
            googlePlusLogin.gotSignResult(data);
        } else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //Custom call
        googlePlusLogin.gotCachedSignIn();
    }

    private void handleResult(GoogleSignInAccount acct) {
        Log.e(TAG, "display name: " + acct.getDisplayName());
        String personPhotoUrl = "";
        String personName = acct.getDisplayName();
        if (acct.getPhotoUrl() != null) {
            personPhotoUrl = acct.getPhotoUrl().toString();
        }
        String email = acct.getEmail();

        Log.e(TAG, "Name: " + personName + ", email: " + email
                + ", Image: " + personPhotoUrl);

        AppController.getInstance().setUserId(acct.getId());
        if (getIntent().getBooleanExtra("isComingFromDetails", false)) {
            onBackPressed();
        } else {

            Intent main = new Intent(LoginActivity.this, HomePageWithMenu.class);
            main.putExtra("name", personName);
            main.putExtra("surname", "");
            main.putExtra("imageUrl", personPhotoUrl);
            startActivity(main);
            finish();
        }
    }

    @Override
    public void onFBLoginSuccess(Profile profile) {
        AppController.getInstance().setUserId(profile.getId());
        nextActivity(profile);
    }

    @Override
    public void mHandleSignInResult(GoogleSignInAccount result) {
        handleResult(result);
    }

    @Override
    public void mConnectionFailed(ConnectionResult connectionResult) {
        googlePlusLogin.hideProgressDialog();
    }

    @Override
    public void onSignInFailed() {
        googlePlusLogin.hideProgressDialog();
    }

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();
            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);
            Log.e("Package Name=", context.getApplicationContext().getPackageName());
            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));
                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }
        return key;
    }
}
