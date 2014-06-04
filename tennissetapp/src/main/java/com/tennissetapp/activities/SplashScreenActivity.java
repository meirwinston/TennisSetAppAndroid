package com.tennissetapp.activities;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.tennissetapp.R;
import com.tennissetapp.rest.Client;
import com.tennissetapp.rest.ServiceResponse;
import com.tennissetapp.Constants;
import com.tennissetapp.utils.GcmUtils;
import com.tennissetapp.utils.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashScreenActivity extends Activity{
    final GcmUtils u = new GcmUtils(this);
    private static final String TAG = SplashScreenActivity.class.getSimpleName();
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                attemptLogin();

            }
        }, 3000);
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Check device for Play Services APK.
        u.checkPlayServices();
    }
	
	private void attemptLogin(){
        if(getIntent() != null){
            String registrationId = getIntent().getStringExtra("registration_id");
            Log.i(getClass().getSimpleName(),"attemptLogin *********** " + registrationId);
        }
//        Utils.cleanPersistentCredentials(this);

        SharedPreferences preferences = getSharedPreferences(Constants.AttributeKeys.CREDENTIALS_SHARE_KEY,Context.MODE_PRIVATE);
        String username = preferences.getString(Constants.AttributeKeys.USERNAME,null);
        String password = preferences.getString(Constants.AttributeKeys.PASSWORD, null);

        ServiceResponse response = Client.getInstance().login(username, password);
        if(Constants.env != Constants.Env.PRODUCTION){
            response = null; //^^ TEST
        }
        Log.d(getClass().getSimpleName(), "THE RESPONSE " + response);
    	if(response == null){
    		toWelcomeActivity();
    	}
    	else if(response.containsKey("errors") || response.containsKey("exception")){
    		toWelcomeActivity();
    	}
    	else{
            Utils.persist(this,username, password,((Number)response.get("userAccountId")).longValue());

            u.register();
    		toTennisMatesActivity();
    	}
	}
	private void toTennisMatesActivity(){
		Intent intent = new Intent(this, TennisMatesActivity.class);
		startActivity(intent);
        finish();
	}
	
	private void toWelcomeActivity(){
		Intent i = new Intent(this, WelcomeActivity.class);
		startActivity(i);
		finish();
	}
}
