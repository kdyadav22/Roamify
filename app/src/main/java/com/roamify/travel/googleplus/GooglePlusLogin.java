package com.roamify.travel.googleplus;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


/**
 * Created by Panalink-03 on 2/14/2018.
 */

public class GooglePlusLogin implements GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 007;
    private ProgressDialog mProgressDialog;
    private String TAG;
    private Activity activity;
    private GooglePlusCallback googlePlusCallback;
    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;
    public GooglePlusLogin(Activity activity) {
        this.activity = activity;
        TAG = this.activity.getLocalClassName();
        try {
            googlePlusCallback = (GooglePlusCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement GooglePlusCallback");
        }
    }

    public void initializeGooglePlus() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .enableAutoManage((FragmentActivity) activity, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    public void customizeGooglePlusButton(SignInButton btnSignIn) {
        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn.setScopes(gso.getScopeArray());
    }

    public void gotCachedSignIn() {
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                googlePlusCallback.mHandleSignInResult(acct);
            } else
                googlePlusCallback.onSignInFailed();
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            //showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    //hideProgressDialog();
                    if (googleSignInResult.isSuccess()) {
                        GoogleSignInAccount acct = googleSignInResult.getSignInAccount();
                        googlePlusCallback.mHandleSignInResult(acct);
                    }else
                        googlePlusCallback.onSignInFailed();
                }
            });
        }
    }

    public void gotSignResult(Intent data) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

        if (result.isSuccess()) {
            Log.e("Google plus Success", "Google plus Success ");
            GoogleSignInAccount acct = result.getSignInAccount();
            googlePlusCallback.mHandleSignInResult(acct);
        } else {
            Log.e("Google plus UnSuccess", "Google plus UnSuccess ");
            googlePlusCallback.onSignInFailed();
        }
    }

    public void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        //updateUI(false);
                    }
                });
    }

    public void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        //updateUI(false);
                    }
                });
    }

    public int mResultCode() {
        return RC_SIGN_IN;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        googlePlusCallback.mConnectionFailed(connectionResult);
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(activity);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    public interface GooglePlusCallback {
        void mHandleSignInResult(GoogleSignInAccount result);
        void mConnectionFailed(ConnectionResult connectionResult);
        void onSignInFailed();
    }
}
