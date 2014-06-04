package com.tennissetapp.fragments;

import com.tennissetapp.activities.BaseFragmentActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

public class BaseFragment extends Fragment{

	public BaseFragment(){
	}

	protected void initBackImageAction(){
		if(getActivity() instanceof BaseFragmentActivity){
			//null pointer exception
			((BaseFragmentActivity)getActivity()).getActivityUtils().getBackImageView().setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					BaseFragment.this.browseToLastFragment();
				}
			});
		}
	}
	
	public void browseToLastFragment(Bundle arguments){
		FragmentManager fragmentManager = getFragmentManager();
		FragmentManager.BackStackEntry backEntry=fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount()-1);
	    Fragment fragment = fragmentManager.findFragmentByTag(backEntry.getName());
	    if(fragment == null) {
	    	Log.e(getClass().getSimpleName(), "browseToLastFragment: fragmentManager.findFragmentByTag('" + backEntry.getName() + "') returns a null fragment, why?");
	    	return;
	    }
		FragmentTransaction t = fragmentManager.beginTransaction();
		t.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		t.replace(getId(), fragment);
		t.addToBackStack(null);
		t.commit();

		if(arguments != null){
	    	if(fragment instanceof BaseFragment){
	    		((BaseFragment)fragment).onResume(arguments);
	    	}
	    }
	}
	
	public void onResume(Bundle arguments){
	}
	
	public void browseToLastFragment(){
		browseToLastFragment(null);
	}
	
}
