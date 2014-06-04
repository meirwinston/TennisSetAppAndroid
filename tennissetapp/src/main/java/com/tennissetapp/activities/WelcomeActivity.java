package com.tennissetapp.activities;

import com.tennissetapp.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomeActivity extends BaseFragmentActivity {  
    
    private SectionsPagerAdapter mSectionsPagerAdapter;  
    private ViewPager mViewPager;
    private TextView disclaimerTextView;
    private Button loginButton,createAccountButton;
    private ImageView pagerDotsImageView;
    
    //--
    
    @Override  
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_welcome);
        
        super.onCreate(savedInstanceState,(String)null,true,false);
        pagerDotsImageView = (ImageView)findViewById(R.id.pager_dots_imageview);
        
	    this.disclaimerTextView = (TextView)findViewById(R.id.disclaimer_textview);
        this.loginButton = (Button)findViewById(R.id.welcom_login_button);
        this.createAccountButton = (Button)findViewById(R.id.welcom_create_account_button);
        
        populateDisclaimer();
        this.loginButton.setOnClickListener(
        	new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
					getActivityUtils().putActivityInBackStack(intent);
					WelcomeActivity.this.startActivity(intent);					
				}
			}
        );
        
        
        createAccountButton.setOnClickListener(
    		new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(WelcomeActivity.this, SignupActivity.class);
					getActivityUtils().putActivityInBackStack(intent);
					WelcomeActivity.this.startActivity(intent);					
				}
			}
        );
        
    // Create the adapter that will return a fragment for each of the three  
        // primary sections of the app.  
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());  
  
        // Set up the ViewPager with the sections adapter.  
        mViewPager = (ViewPager) findViewById(R.id.courts_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter); 
        
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int index) {
				Log.d(getClass().getSimpleName(), "onPageSelected::: " + index);
				switch(index){
				case 0:
					pagerDotsImageView.setImageDrawable(getResources().getDrawable(R.drawable.three_dots_first_selected));
					break;
				case 1:
					pagerDotsImageView.setImageDrawable(getResources().getDrawable(R.drawable.three_dots_second_selected));
					break;
				case 2:
					pagerDotsImageView.setImageDrawable(getResources().getDrawable(R.drawable.three_dots_third_selected));
					break;
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
    }  

    private void populateDisclaimer(){
//    	String disclaimerString = "We deeply care about your privacy\nTerms of use and Privacy Policy"; 
    	ClickableSpan termsOfUseSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Log.i(getClass().getSimpleName(), "CLICK termsOfUseSpan!!!");
            }
            
            @Override
            public void updateDrawState(TextPaint ds) {
            	ds.setColor(getResources().getColor(R.color.turquoise));
                ds.setUnderlineText(false);
            }
        };
        
        ClickableSpan privacyPolicySpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Log.i(getClass().getSimpleName(),"CLICK privacyPolicySpan!!!");
            }
            
            @Override
            public void updateDrawState(TextPaint ds) {
            	ds.setColor(getResources().getColor(R.color.turquoise));
                ds.setUnderlineText(false);
            }
        };

        SpannableString s = SpannableString.valueOf(this.disclaimerTextView.getText());
        s.setSpan(termsOfUseSpan, 34, 46, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        s.setSpan(privacyPolicySpan, 51, 65, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//14
    	this.disclaimerTextView.setText(s);
    }
    
    
  
    public static class SectionsPagerAdapter extends FragmentPagerAdapter {  
    	
        public SectionsPagerAdapter(FragmentManager fm) {  
            super(fm);  
        }  
  
        @Override  
        public Fragment getItem(int position) {
        	Fragment f = null;
        	switch(position){
        	case 0:
        		f = new LoginFragment1();
        		break;
        	case 1:
        		f = new LoginFragment2();
        		break;
        	case 2:
        		f = new LoginFragment3();
        		break;
        	}
        	return f;
        }  
  
        @Override  
        public int getCount() {  
            return 3;
        }  
  
    }  
  
    public static class LoginFragment1 extends Fragment{
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_welcome_page1, container, false);
            return rootView;  
        }  
    }  
  
    
    public static class LoginFragment2 extends Fragment{
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_welcome_page2, container, false);
			return rootView;
		}
    }
    
    public static class LoginFragment3 extends Fragment{
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_welcome_page3, container, false);
			return rootView;
		}
    }
}  