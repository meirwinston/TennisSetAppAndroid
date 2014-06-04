package com.tennissetapp.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

/**
 * edit text loses its focus when FragmentTabHost is present
 * @author mwinston
 *
 */
public class FragmentTabHost extends android.support.v4.app.FragmentTabHost {

	public FragmentTabHost(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FragmentTabHost(Context context) {
        super(context);
    }
    
//	@Override
//	public void onTouchModeChanged(boolean isInTouchMode) {
//		// leave it empty here. It looks that when you use hard keyboard,
//		// this method will be called and the focus will be token.
////		C.logd("The current tab is " + getCurrentTab());
//	}
	
	

//	@Override
//	public boolean onKeyPreIme(int keyCode, KeyEvent event) {
////		// TODO Auto-generated method stub
////		return super.onKeyPreIme(keyCode, event);
//		C.logd("onKeyPreIme");
//		return false;
//	}
//
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
////		// TODO Auto-generated method stub
////		return super.onKeyDown(keyCode, event);
//		C.logd("onKeyDown");
//		return false;
//	}
//
//	@Override
//	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
////		return super.onKeyLongPress(keyCode, event);
//		
//		C.logd("onKeyLongPress");
//		return false;
//	}
//
//	@Override
//	public boolean onKeyUp(int keyCode, KeyEvent event) {
////		return super.onKeyUp(keyCode, event);
//		
//		C.logd("onKeyUp");
//		return false;
//	}
//
//	@Override
//	public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
////		return super.onKeyMultiple(keyCode, repeatCount, event);
//		C.logd("onKeyMultiple");
//		return false;
//	}
//
//	@Override
//	public boolean onKeyShortcut(int keyCode, KeyEvent event) {
////		return super.onKeyShortcut(keyCode, event);
//		
//		C.logd("onKeyShortcut");
//		return false;
//	}
	
	
	
	
}
