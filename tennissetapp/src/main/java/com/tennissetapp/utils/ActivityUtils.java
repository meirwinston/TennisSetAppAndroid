package com.tennissetapp.utils;

import java.util.LinkedList;

import com.tennissetapp.R;
import com.tennissetapp.Constants;
import com.tennissetapp.activities.MessagesActivity;
import com.tennissetapp.activities.PlayerProfileActivity;
import com.tennissetapp.activities.TennisMatesActivity;
import com.tennissetapp.activities.WelcomeActivity;
import com.tennissetapp.rest.Client;
import com.tennissetapp.rest.ServiceResponse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityUtils {
	private Activity activity;
	private static String TAG ;
	private static final LinkedList<Class<?>> backStackQueue = new LinkedList<Class<?>>();
	
	public ActivityUtils(Activity activity){
		this.activity = activity;
        TAG = activity.getClass().getSimpleName();
		if(activity == null){
			throw new NullPointerException("ActivityUtils activity argument cannot be null");
		}
	}
	public void putActivityInBackStack(Intent intent){
        backStackQueue.push(activity.getClass());
	}
	
	public void putFragmentInBackStack(Fragment fragment){
		backStackQueue.push(fragment.getClass());
	}
	
	public void setLeftButtonBackAction(){
		getBackImageView().setImageResource(R.drawable.arrow_left);
		getBackImageView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				broweToLastActivity();
				
			}
		});
	}
	
	public void browseToLastFragment(){
//		FragmentManager fragmentManager = ((FragmentActivity)activity).getFragmentManager();
//		FragmentManager.BackStackEntry backEntry=fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount()-1);
//	    Fragment fragment = fragmentManager.findFragmentByTag(backEntry.getName());
//	    if(fragment == null) {
//	    	C.loge("browseToLastFragment: fragmentManager.findFragmentByTag('" +backEntry.getName() +  "') returns a null fragment, why?");
//	    	return;
//	    }
//		FragmentTransaction t = fragmentManager.beginTransaction();
//		t.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//		t.replace(getId(), fragment);
//		t.addToBackStack(null);
//		t.commit();
//
//		if(arguments != null){
//	    	if(fragment instanceof BaseFragment){
//	    		((BaseFragment)fragment).onResume(arguments);
//	    	}
//	    }
	}
	
	public void setBackStackFragment(final Fragment f){
//		getBackImageView().setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				FragmentTransaction t = f.getFragmentManager().beginTransaction();
//				t.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//				Fragment fragment = f.getFragmentManager().findFragmentByTag(TennisMatchesActivity.SEARCH_FRAGMENT_TAG);
//				t.replace(f.getId(), fragment);
//				t.addToBackStack(null);
//				t.commit();
//
//			}
//		});
	}
	
//	protected void broseToLastFragment(Fragment f){
//		Class<?> c = backStackQueue.pop();
//		if(c != null){
//			if(Fragment.class.isAssignableFrom(c)){
//				FragmentManager.BackStackEntry backEntry=f.getFragmentManager().getBackStackEntryAt(f.getFragmentManager().getBackStackEntryCount()-1);
//			    Fragment toFragment = f.getFragmentManager().findFragmentByTag(backEntry.getName());
//			    if(toFragment == null) {
//			    	C.loge("browseToLastFragment: fragmentManager.findFragmentByTag('" +backEntry.getName() +  "') returns a null fragment, why?");
//			    	return;
//			    }
//				FragmentTransaction t = f.getFragmentManager().beginTransaction();
//				t.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//				t.replace(f.getId(), toFragment);
//				t.addToBackStack(null);
//				t.commit();
////				android.support.v4.app.FragmentManager m = ((android.support.v4.app.Fragment)activity).getFragmentManager();
//		
//			}
//		}
//		else{
//			C.loge("broweToLastActivity null source");
//		}
//	}

	public void broweToLastActivity(){
		Class<?> c = backStackQueue.pop();
		if(c != null){
			if(Fragment.class.isAssignableFrom(c)){
				
			}
			else if(Activity.class.isAssignableFrom(c)){
				Intent intent = new Intent(activity,c);
				activity.startActivity(intent);
			}
		}
		else{
            Log.e(getClass().getSimpleName(),"broweToLastActivity null source");
		}
	}
	
//	protected void broweToLastActivity(){
//		Intent intent = activity.getIntent();
//		Class<?> c = (Class<?>)intent.getSerializableExtra("source");
//		if(c != null){
//			intent = new Intent(activity,c);
//			activity.startActivity(intent);
//		}
//		else{
//			C.loge("broweToLastActivity null source");
//
//		}
//	}
	
	public void toastServerIsDown(){
		Toast.makeText(activity, "The server is down, please try again later!",  Toast.LENGTH_LONG).show();
	}
	
	public void setActionBarTitle(String title){
		((TextView)activity.findViewById(R.id.action_bar_title)).setText(title);
	}
	
	public void setActionBarTitle(int titleResourceId){
		((TextView)activity.findViewById(R.id.action_bar_title)).setText(titleResourceId);
	}
	
	public ImageView getBackImageView(){
		return (ImageView)activity.findViewById(R.id.action_bar_back_icon);
	}
	
	
	//-----------------------
	
	private DrawerLayout navigationDrawerLayout;
	private ListView navigationDrawerList;
    private ImageView leftImageView;
	public void initDrawer(){
		leftImageView = getBackImageView();
		leftImageView.setImageResource(R.drawable.ic_drawer);
		leftImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!navigationDrawerLayout.isDrawerOpen(navigationDrawerList)){
					navigationDrawerLayout.openDrawer(navigationDrawerList);
				}
				else{
					navigationDrawerLayout.closeDrawer(navigationDrawerList);
				}
			}
		});
		navigationDrawerLayout = (DrawerLayout)activity.findViewById(R.id.drawer_layout);
		navigationDrawerList = (ListView)activity.findViewById(R.id.left_drawer);
	    
	 // set a custom shadow that overlays the main content when the drawer opens
        navigationDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        navigationDrawerList.setAdapter(new NavigationListAdapter(activity));
        navigationDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        navigationDrawerList.setChoiceMode(ListView.CHOICE_MODE_NONE);
        
	}
	
	private static class NavigationListAdapter extends ArrayAdapter<String>{
		SparseIntArray icons = new SparseIntArray(5);
		
		public NavigationListAdapter(Context context) {
			super(context, R.layout.drawer_list_item, context.getResources().getStringArray(R.array.navigation_menu));
			
			icons.put(1, R.drawable.player_profile);
			icons.put(2, R.drawable.profile_edit);
			icons.put(3, R.drawable.profile_mates);
			icons.put(5, R.drawable.green_ball);
			icons.put(6, R.drawable.green_envelope);
		}

		@Override
		public boolean isEnabled(int position) {
			if(position == 0 || position == 4){
				return false;
			}
			return true;
		}



		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView view =  (TextView)super.getView(position, convertView, parent);
			if(position == 0 || position == 4){
				view.setEnabled(false);
				view.setBackgroundResource(R.color.turquoise);
				view.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
				view.setTextColor(Color.WHITE);
//				view.setTypeface(Typeface.DEFAULT_BOLD);
				view.setHeight(LayoutParams.WRAP_CONTENT);
				view.setMinHeight(0);
			}
			else{
				view.setCompoundDrawablesWithIntrinsicBounds(icons.get(position), 0, 0, 0);
			}
			
			return view;
		}
	}
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		private void startPlayerProfileActivity(){
			Intent intent = new Intent(activity,PlayerProfileActivity.class);
			intent.putExtra("userAccountId", Utils.getUserAccountId(activity));
    		activity.startActivity(intent);
		}
		
		private void startEditPlayerProfileActivity(){
			Intent intent = new Intent(activity,PlayerProfileActivity.class);
			intent.putExtra("userAccountId", Utils.getUserAccountId(activity));
    		activity.startActivity(intent);
		}
		
		private void startTennisMatesActivity(){
			Intent intent = new Intent(activity,TennisMatesActivity.class);
    		activity.startActivity(intent);
		}
		
		private void startMessagesActivity(){
			Intent intent = new Intent(activity,MessagesActivity.class);
    		activity.startActivity(intent);
		}
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	navigationDrawerLayout.closeDrawer(navigationDrawerList);
        	switch(position){
        	case 1:
        		startPlayerProfileActivity();
        		break;
        	case 2:
        		startEditPlayerProfileActivity();
        		break;
        	case 3:
        		startTennisMatesActivity();
        	case 5:
//        		startNotificationsActivity();
        		break;
        	case 6:
        		startMessagesActivity();
        		break;
        		
        	}
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = activity.getMenuInflater();
        menu.add("Terms of Use").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Log.d(TAG,"On Terms of Use");
                return false;
            }
        });
        menu.add("Privacy Policy").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Log.d(TAG,"On Privacy Policy");
                return false;
            }
        });
        menu.add("Logout").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Log.i(TAG, "on logout");
                Intent intent = new Intent(activity, WelcomeActivity.class);
                ServiceResponse response = Client.getInstance().logout();
                if(response == null){
                    return false;
                }
                else if(response.containsKey("errors") || response.containsKey("exception")){
                    return false;
                }
                else{
                    Utils.cleanPersistentCredentials(activity);
                    activity.startActivity(intent);
                    activity.finish();
                    Log.i(TAG, "on logout FINISH" );
                    return true;
                }
            }
        });
        inflater.inflate(R.menu.tennis_mates, menu);
        return true;
    }
}
