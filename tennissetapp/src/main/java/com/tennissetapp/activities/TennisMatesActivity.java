package com.tennissetapp.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import com.tennissetapp.R;
import com.tennissetapp.fragments.FavoritesMatesFragment;
import com.tennissetapp.fragments.NearbyMatesFragment;
import com.tennissetapp.fragments.SearchMatesFragment;

public class TennisMatesActivity extends BaseFragmentActivity {
	private FragmentTabHost tabHost;
	private View favoritesTabIndicator,searchTabIndicator,nearbyTabIndicator;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_tennis_mates);
		super.onCreate(savedInstanceState);
		getActivityUtils().initDrawer();
		initTabs();
	}
	
	private void initTabs(){
		tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
		tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		TabSpec tabSpec = tabHost.newTabSpec(getResources().getString(R.string.tag_mates_favorites));
		favoritesTabIndicator = createTabView("Favorites",R.drawable.selector_favorite_mates_tab_icon);
		tabSpec.setIndicator(favoritesTabIndicator);

		tabHost.addTab(tabSpec,FavoritesMatesFragment.class,null);

		tabSpec = tabHost.newTabSpec(getResources().getString(R.string.tag_mates_nearby));
		nearbyTabIndicator = createTabView("Nearby",R.drawable.selector_nearyby_mates_tab_icon);
		tabSpec.setIndicator(nearbyTabIndicator);
		tabHost.addTab(tabSpec,NearbyMatesFragment.class,null);

		tabSpec = tabHost.newTabSpec(getResources().getString(R.string.tag_mates_search));
		searchTabIndicator = createTabView("Search",R.drawable.selector_search_mates_tab_icon);
		tabSpec.setIndicator(searchTabIndicator);
		tabHost.addTab(tabSpec,SearchMatesFragment.class,new Bundle());


		if(tabHost.getTabWidget() != null){
			tabHost.getTabWidget().setStripEnabled(false);
			tabHost.getTabWidget().setDividerDrawable(null);
		}

		tabHost.setCurrentTab(1);
	}
	
	public SearchMatesFragment.OnLoad onLoad = null;
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		MenuInflater inflater = getMenuInflater();
//		menu.add("Terms of Use");
//		menu.add("Privacy Policy");
//		menu.add("Logout");
//		inflater.inflate(R.menu.tennis_mates, menu);
//		return true;
//	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Toast.makeText(this, "Just a env", Toast.LENGTH_SHORT).show();
		return true;
	}
	
	
	
	public View getNearbyTabIndicator(){
		return this.nearbyTabIndicator;
	}
	
	public View getSearchTabIndicator(){
		return searchTabIndicator;
	}
	
	public View getCreateTabIndicator(){
		return favoritesTabIndicator;
	}
	
	private View createTabView(String text,int drawableResource) {
		View view = LayoutInflater.from(tabHost.getContext()).inflate(R.layout.tabs_bg, null);
		TextView tv = (TextView) view.findViewById(R.id.tabs_bg_textview);
	//set 0 where you don't want images
		tv.setCompoundDrawablesWithIntrinsicBounds(drawableResource, 0, 0, 0);
		tv.setText(text);
		return view;
	}
}

