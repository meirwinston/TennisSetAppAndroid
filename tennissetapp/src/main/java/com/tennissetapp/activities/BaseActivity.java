package com.tennissetapp.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.tennissetapp.R;
import com.tennissetapp.utils.ActivityUtils;
import com.tennissetapp.utils.Utils;

public abstract class BaseActivity extends Activity{
	private ActivityUtils activityUtils = new ActivityUtils(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		onCreate(savedInstanceState,null,true);
	}
	
	protected void onCreate(Bundle savedInstanceState,String title, boolean iconVisible) {
		this.onCreate(savedInstanceState,title,iconVisible,true);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return activityUtils.onCreateOptionsMenu(menu);
    }
	
	protected void onCreate(Bundle savedInstanceState,String title, boolean iconVisible, boolean backIconVisible) {
		//title bar 
		if(getActionBar() != null){
			getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
			getActionBar().setCustomView(R.layout.title_action_bar);
			if(title != null){
				activityUtils.setActionBarTitle(title);
			}
			Utils.setLogoVisible(this,iconVisible);
			Utils.setBackIconVisible(this, backIconVisible);
		}
		
		super.onCreate(savedInstanceState);
	}
	
	public ActivityUtils getActivityUtils(){
		return activityUtils;
	}
	
	protected void onCreate(Bundle savedInstanceState,Integer titleResourceId, boolean iconVisible, boolean backIconVisible) {
		//title bar 
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		getActionBar().setCustomView(R.layout.title_action_bar);
		if(titleResourceId != null){
			activityUtils.setActionBarTitle(titleResourceId);
		}
		Utils.setLogoVisible(this,iconVisible);
		Utils.setBackIconVisible(this,backIconVisible);
		
		super.onCreate(savedInstanceState);
	}
}
