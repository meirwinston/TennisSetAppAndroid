package com.tennissetapp.activities;

import com.tennissetapp.Constants;
import com.tennissetapp.R;
import com.tennissetapp.TestConstants;
import com.tennissetapp.rest.Client;
import com.tennissetapp.rest.ServiceResponse;
import com.tennissetapp.utils.GcmUtils;
import com.tennissetapp.utils.Utils;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {  
	private EditText emailEditText, passwordEditText;
    private GcmUtils gcmUtils = new GcmUtils(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.string.title_activity_login,false,true);
		setContentView(R.layout.activity_login);
		emailEditText = (EditText)findViewById(R.id.email_edittext);
		passwordEditText = (EditText)findViewById(R.id.password_edittext);

        Log.i(getClass().getSimpleName(),"onCreate");
		findViewById(R.id.login_button).setOnClickListener(
			new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					attemptLogin();
				}
		});
		
		getActivityUtils().getBackImageView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivityUtils().broweToLastActivity();
			}
		});
		
		if(Constants.env != Constants.Env.PRODUCTION){
			test();
		}
	}

    @Override
    protected void onResume() {
        super.onResume();
        // Check device for Play Services APK.
        gcmUtils.checkPlayServices();
    }
	
	private void test(){
//		emailEditText.setText("vburton@roomm.edu");
//		emailEditText.setText(TestConstants.email(0));
        emailEditText.setText("meirwinston@yahoo.com");
		passwordEditText.setText("111111");
		Location location = Utils.getDeviceLocation(this);
		if(location != null){
			Toast.makeText(this, "Location is: (" + location.getLatitude() + ", " + location.getLongitude() + ")", Toast.LENGTH_LONG).show();
		}
	}

//	private void persist(String username, String password,Long userAccountId){
//		SharedPreferences preferences = getSharedPreferences(Constants.AttributeKeys.CREDENTIALS_SHARE_KEY,Context.MODE_PRIVATE);
//		SharedPreferences.Editor editor = preferences.edit();
//		editor.putString(Constants.AttributeKeys.USERNAME, username);
//		editor.putString(Constants.AttributeKeys.PASSWORD, password);
//		editor.putLong("userAccountId", userAccountId);
//		editor.putLong(Constants.AttributeKeys.DATETIME, System.currentTimeMillis());
//		editor.commit();
//
//		//get the vlaue
//		//String saved_value=sp.getString("share_key",null);
//	}

    private void attemptLogin(){
        if(getIntent() != null){
            String registrationId = getIntent().getStringExtra("registration_id");
            Log.i(getClass().getSimpleName(),"attemptLogin *********** " + registrationId);
        }


//    	C.logi("HERER " + emailEditText + ", " + passwordEditText);
    	String username = emailEditText.getText().toString();
    	String password = passwordEditText.getText().toString();
    	
    	ServiceResponse response = Client.getInstance().login(username, password);
    	if(response == null){
    		Utils.toastServerIsDown(this);
    	}
    	else if(response.containsKey("errors") || response.containsKey("exception")){
    		Utils.popupErrors(this, response);
    	}
    	else{
            Log.i(getClass().getSimpleName(),"BEFORE000**** " + response.get("userAccountId"));
    		Utils.persist(this, username, password, ((Number) response.get("userAccountId")).longValue());
            Log.i(getClass().getSimpleName(),"BEFORE**** ");
            gcmUtils.register();
            Log.i(getClass().getSimpleName(), "Starting TennisMatesActivity...");
    		Intent intent = new Intent(LoginActivity.this, TennisMatesActivity.class);
    		LoginActivity.this.startActivity(intent);
    	}
    }
}