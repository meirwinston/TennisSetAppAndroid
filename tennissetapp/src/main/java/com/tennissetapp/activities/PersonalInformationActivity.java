package com.tennissetapp.activities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import com.tennissetapp.Constants;
import com.tennissetapp.R;
import com.tennissetapp.TestConstants;
import com.tennissetapp.form.UpdateAccountPrimaryForm;
import com.tennissetapp.json.GeoCodeResult;
import com.tennissetapp.json.JacksonUtils;
import com.tennissetapp.rest.Client;
import com.tennissetapp.rest.ServiceResponse;
import com.tennissetapp.ui.PlacesAutoCompleteAdapter;
import com.tennissetapp.utils.Utils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class PersonalInformationActivity extends BaseActivity {
	private TextView genderTextView,birthdayTextView,firstNameEditText,lastNameEditText;
	private AutoCompleteTextView addressAutoCompleteTextView;
	private GeoCodeResult.Result geoLocation;
	private int year, month, day;
	private DatePickerDialog datePickerDialog;
	private Button submitButton;
	
	private OnDateSetListener onDateSetListener = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			DateFormat format = SimpleDateFormat.getDateInstance();
			Calendar cal = Calendar.getInstance();
			cal.set(year,monthOfYear,dayOfMonth);
			day = dayOfMonth ;
			month = monthOfYear + 1;
			PersonalInformationActivity.this.year = year;
			birthdayTextView.setText(format.format(cal.getTime()));
			
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.string.title_personal_information,false,true);
		setContentView(R.layout.activity_personal_information);
		
		this.firstNameEditText = (EditText)findViewById(R.id.tennis_level_textview);
		this.lastNameEditText = (EditText)findViewById(R.id.hand_edittext);
		this.submitButton = (Button)findViewById(R.id.submit_button);
		
		
		initGender();
		initAutoComplete();
		initBirthDate();
		
		this.submitButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				submit();
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
	
	private void test(){
        Random r = new Random();
        int index = r.nextInt(TestConstants.Names.length);
        String[] gen = {"Male","Female"};
		this.firstNameEditText.setText(TestConstants.firstName(index));
		this.lastNameEditText.setText(TestConstants.lastName(index));
		this.genderTextView.setText(gen[r.nextInt(1)]);
		onDateSetListener.onDateSet(null, 1960 + r.nextInt(40), r.nextInt(12), r.nextInt(28));
	}
	
	private void submit(){
		UpdateAccountPrimaryForm form = toForm();
		Log.d(getClass().getSimpleName(), "submit " + form);
		
		this.submitButton.setEnabled(false);
		ServiceResponse response = Client.getInstance().updateAccountPrimaryFields(form);
		
		if(response.containsKey("exception")){
			this.submitButton.setEnabled(true);
			if("com.tennissetapp.exception.NotAuthorizedException".equals(response.get("code"))){
				Intent intent = new Intent(this, SignupActivity.class);
				this.startActivity(intent);
			}
		}
		else if(response.containsKey("errors")){
			this.submitButton.setEnabled(true);
			Utils.popupErrors(this, response);
		}
		else{
//			Intent intent = new Intent(this, TennisDetailsActivity.class);
//			this.startActivity(intent);
			
			Intent intent = new Intent(this, TennisMatesActivity.class);
			intent.putExtra("source", PersonalInformationActivity.class);
			this.startActivity(intent);
			finish();
			
		}
	}
	
	
	
	private void initBirthDate(){
		final Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, c.get(Calendar.YEAR)-20);
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		
		this.datePickerDialog = new DatePickerDialog(this, this.onDateSetListener, year, month, day);
		datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()-315360000000l); //-10 years
		this.birthdayTextView = (TextView)findViewById(R.id.type_of_play_textview);
		this.birthdayTextView.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view) {
				
				datePickerDialog.show();
			}
		});
	}
	
	private void initAutoComplete(){
		addressAutoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.availability_textview);
		this.addressAutoCompleteTextView.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.listitem));
		addressAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		        geoLocation = (GeoCodeResult.Result) adapterView.getItemAtPosition(position);
		    }
		});
	}
	
	private void showGenderDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final View view = LayoutInflater.from(this).inflate(R.layout.dialog_gender, null);
        builder.setView(view);
        final AlertDialog dialog = builder.show();
        
        final String[] arr = getResources().getStringArray(R.array.profile_gender);
        
        view.findViewById(R.id.male_layout).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				genderTextView.setText(arr[0]);
				dialog.dismiss();
			}
		});
        
        view.findViewById(R.id.female_layout).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				genderTextView.setText(arr[1]);
				dialog.dismiss();
			}
		});
        
	
	}
	
	private void initGender(){
		genderTextView = (TextView)findViewById(R.id.type_of_match_textview);
		genderTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showGenderDialog();
			}
		});
		
		
//		genderTextView.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				AlertDialog.Builder builder = new AlertDialog.Builder(PersonalInformationActivity.this);
//	            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PersonalInformationActivity.this,android.R.layout.select_dialog_singlechoice,getResources().getStringArray(R.array.profile_gender));
//	            builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						genderTextView.setText(arrayAdapter.getItem(which));
//						dialog.dismiss();
//					}
//				});
//	            
//	            builder.show();
//			}
//		});
	}
	
	private String getGender(){
		if(this.genderTextView.getText().equals("Male")){
			return "MALE";
		}
		else if(this.genderTextView.getText().equals("Female")){
			return "FEMALE";
		} 
		return null;
	}
	
	private UpdateAccountPrimaryForm toForm(){
		UpdateAccountPrimaryForm f = new UpdateAccountPrimaryForm();
//		f.userAccountId = this.userAccountId;
		f.firstName = this.firstNameEditText.getText().toString();
		f.lastName = this.lastNameEditText.getText().toString();
		f.gender = getGender();
		f.agreesToTerms = "true";
		
		//birthdate
		f.birthDay = String.valueOf(day);
		f.birthMonth = String.valueOf(month);
		f.birthYear = String.valueOf(year);
		
		if(Constants.env != Constants.Env.PRODUCTION){
			//-----TEST to work offline
			String s = "{\"address_components\" : [{\"long_name\" : \"1600\",\"short_name\" : \"1600\",\"types\" : [ \"street_number\" ]},{\"long_name\" : \"Amphitheatre Parkway\",\"short_name\" : \"Amphitheatre Pkwy\",\"types\" : [ \"route\" ]},{\"long_name\" : \"Mountain View\",\"short_name\" : \"Mountain View\",\"types\" : [ \"locality\", \"political\" ]},{\"long_name\" : \"Santa Clara\",\"short_name\" : \"Santa Clara\",\"types\" : [ \"administrative_area_level_2\", \"political\" ]},{\"long_name\" : \"California\",\"short_name\" : \"CA\",\"types\" : [ \"administrative_area_level_1\", \"political\" ]},{\"long_name\" : \"United States\",\"short_name\" : \"US\",\"types\" : [ \"country\", \"political\" ]},{\"long_name\" : \"94043\",\"short_name\" : \"94043\",\"types\" : [ \"postal_code\" ]}],\"formatted_address\" : \"1600 Amphitheatre Parkway, Mountain View, CA 94043, USA\",\"geometry\" : {\"location\" : {\"lat\" : 37.4219988,\"lng\" : -122.083954},\"location_type\" : \"ROOFTOP\",\"viewport\" : {\"northeast\" : {\"lat\" : 37.42334778029149,\"lng\" : -122.0826050197085},\"southwest\" : {\"lat\" : 37.42064981970849,\"lng\" : -122.0853029802915}}},\"types\" : [ \"street_address\" ]}";
			try {
				this.geoLocation = JacksonUtils.deserializeAs(s, GeoCodeResult.Result.class);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			//-----	
		}
		
		if(this.geoLocation != null){
			f.populate(this.geoLocation);
		}
		return f;
	}
}
