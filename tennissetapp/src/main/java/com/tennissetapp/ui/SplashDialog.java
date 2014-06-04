package com.tennissetapp.ui;

import com.tennissetapp.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class SplashDialog {
	private Dialog splashDialog; 
	private ImageView splashImageView;
	private int splashTimeout = 3000;
	private Context context;
	private Runnable onVanishRunnable;
	private Listener onRemoverListener;
	
	public interface Listener{
		void onRemove();
	}
	
	public void setOnRemoveListener(Listener listener){
		this.onRemoverListener = listener;
	}
	
	public SplashDialog(Context context) {
		this.context = context;
	    splashImageView = new ImageView(context);
	    LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	    splashImageView.setLayoutParams(params);
	    splashImageView.setImageResource(R.drawable.splash);
	}
	 
	/**
	 * Removes the Dialog that displays the splash screen
	 */
	public void removeSplashScreen() {
	    if (splashDialog != null) {
	        splashDialog.dismiss();
	        splashDialog = null;
	    }
	}
	
	public void setOnVanishRunnable(Runnable onVanishRunnable){
		this.onVanishRunnable = onVanishRunnable;
	}
	
	/**
	 * Shows the splash screen over the full Activity
	 */
	public void show(int timeout) {
		this.splashTimeout = timeout;
		
		splashDialog = new Dialog(this.context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
		splashDialog.setContentView(splashImageView);
		splashDialog.setCancelable(false);
		splashDialog.show();

		// Set Runnable to remove splash screen just in case
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				if(onRemoverListener != null){
					onRemoverListener.onRemove();
				}
				removeSplashScreen();
			}
		}, splashTimeout);
	}
}
