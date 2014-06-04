package com.tennissetapp.activities;

import java.util.Random;
import java.util.UUID;

import com.tennissetapp.Constants;
import com.tennissetapp.R;
import com.tennissetapp.TestConstants;
import com.tennissetapp.form.SignupForm;
import com.tennissetapp.rest.Client;
import com.tennissetapp.rest.ServiceResponse;
import com.tennissetapp.utils.GcmUtils;
import com.tennissetapp.utils.Utils;
import android.os.Bundle;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignupActivity extends BaseActivity {
	private EditText emailEditText,passwordEditText,repeatPasswordEditText;
	private Button signupButton;
	private TextView disclaimerTextView;
    private GcmUtils gcmUtils = new GcmUtils(this);
    private static final String TAG = SignupActivity.class.getSimpleName();
	
	private void test(){
		//TEST
        Random r = new Random();
		emailEditText.setText(TestConstants.email(r.nextInt(TestConstants.Names.length)));
		passwordEditText.setText("111111");
		repeatPasswordEditText.setText("111111");		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.string.title_activity_create_an_account,false,true);
		setContentView(R.layout.activity_signup);
		
		emailEditText = (EditText)findViewById(R.id.email_edittext);
		passwordEditText = (EditText)findViewById(R.id.password_edittext);
		repeatPasswordEditText = (EditText)findViewById(R.id.repeat_password_edittext);
		disclaimerTextView = (TextView)findViewById(R.id.signup_disclaimer_textview);
		signupButton = (Button)findViewById(R.id.signup_submit_button);
		populateDisclaimer();
		
		signupButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				signup();
			}
		});
		
		getActivityUtils().getBackImageView().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivityUtils().broweToLastActivity();
			}
		});
		if(Constants.env != Constants.Env.PRODUCTION){
			test();
		}
		
	}
	
	private void signup(){
		SignupForm form = new SignupForm();
		form.email = emailEditText.getText().toString();
		form.password = passwordEditText.getText().toString();
		form.confirmPassword = repeatPasswordEditText.getText().toString();
		this.signupButton.setEnabled(false);
		ServiceResponse response = Client.getInstance().signup(form);
		Log.d(getClass().getSimpleName(), "signup response " + response);
		if(response == null){
			this.signupButton.setEnabled(true);
			Utils.toastServerIsDown(this);
		}
		else if(response.containsKey("errors") || response.containsKey("exception")){
			//fail
//			Log.i(C.LogTag,"FAILED SIGNUP " + response.get("errors") + ", "+ response.get("exception"));
//			showErrors(response);
			this.signupButton.setEnabled(true);
			Utils.popupErrors(this, response);
		}
		else{

            Utils.persist(this, form.email, form.password, ((Number) response.get("userAccountId")).longValue());
            gcmUtils.register();

			Intent intent = new Intent(SignupActivity.this, PersonalInformationActivity.class);
            intent.putExtra("userAccountId", ((Number)response.get("userAccountId")).longValue());
			getActivityUtils().putActivityInBackStack(intent);
			this.startActivity(intent);
            this.finish();
		}

	}
	
	private void populateDisclaimer(){
//    	String disclaimerString = "We deeply care about your privacy\nTerms of use and Privacy Policy"; 
    	ClickableSpan termsOfUseSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Log.i(getClass().getSimpleName(),"CLICK termsOfUseSpan!!!");
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
                Log.i(getClass().getSimpleName(), "CLICK privacyPolicySpan!!!");
            }
            
            @Override
            public void updateDrawState(TextPaint ds) {
            	ds.setColor(getResources().getColor(R.color.turquoise));
                ds.setUnderlineText(false);
            }
        };
        String str = this.disclaimerTextView.getText().toString();
        SpannableString s = SpannableString.valueOf(this.disclaimerTextView.getText());
        s.setSpan(termsOfUseSpan, str.indexOf("Terms of Use"), str.indexOf("Terms of Use") + "Terms of Use".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        s.setSpan(privacyPolicySpan, str.indexOf("Privacy Policy"), str.indexOf("Privacy Policy") + "Privacy Policy".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//14
    	this.disclaimerTextView.setText(s);
    }

}
