package com.roamify.travel.facebook;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.*;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by Panalink-03 on 2/14/2018.
 */

/*
* Note:- To use this custom method for Facebook Login...Please write below code in your Activity
*    private CallbackManager mCallbackManager; (Declare in your actiivty)
*    mCallbackManager = CallbackManager.Factory.create(); (Instanciate in onCreate() or onStart())
*
*    //Also override this method
*    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
*
* */

public class FaceBookLogin {
    private Activity activity;
    private CallbackManager mCallbackManager;
    private MFacebookCallback mFacebookCallback;

    public FaceBookLogin(Activity activity, CallbackManager mCallbackManager) {
        this.activity = activity;
        this.mCallbackManager = mCallbackManager;
        try {
            mFacebookCallback = (MFacebookCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement GooglePlusCallback");
        }
    }

    public void onFblogin() {
        // Set permissions
        LoginManager.getInstance().logOut();
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("email", "public_profile", "user_photos"));
        LoginManager.getInstance().registerCallback(mCallbackManager, new com.facebook.FacebookCallback<LoginResult>() {
            private ProfileTracker mProfileTracker;
            @Override
            public void onSuccess(final LoginResult loginResult) {
                Profile profile = Profile.getCurrentProfile();
                if (profile != null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            Log.v("facebook - profile", currentProfile.getFirstName());
                            mProfileTracker.stopTracking();
                            mFacebookCallback.onFBLoginSuccess(currentProfile);
                        }
                    };
                }else {
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,email,gender, birthday");
                    GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {
                                    try {
                                        //AccessToken accessToken = loginResult.getAccessToken();
                                        Profile profile = Profile.getCurrentProfile();
                                        mFacebookCallback.onFBLoginSuccess(profile);
                                    } catch (Exception e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            });
                    request.setParameters(parameters);
                    request.executeAsync();
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(activity, "Login Cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(activity, "Login Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    public interface MFacebookCallback {
        void onFBLoginSuccess(Profile profile);
    }
}
