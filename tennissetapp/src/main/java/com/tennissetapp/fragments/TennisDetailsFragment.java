package com.tennissetapp.fragments;

import java.util.ArrayList;
import org.apache.commons.lang.StringUtils;

import com.tennissetapp.Constants;
import com.tennissetapp.R;
import com.tennissetapp.activities.BaseFragmentActivity;
import com.tennissetapp.activities.SignupActivity;
import com.tennissetapp.activities.TennisMatesActivity;
import com.tennissetapp.form.UpdateTennisDetailsForm;
import com.tennissetapp.rest.Client;
import com.tennissetapp.rest.ServiceResponse;
import com.tennissetapp.utils.Utils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class TennisDetailsFragment extends BaseFragment{
	private TextView typeOfMatchTextView,tennisLevelTextView,handTextView,typeOfPlayTextView;
	private TextView availabilityTextView;
	private Button submitButton;
	private TennisLevelFragment tennisLevelFragment = new TennisLevelFragment();
	private boolean singles,doubles,fullMatch,points,hittingAround;
	private String hand;
	private Float tennisLevel;
	private boolean weekdayEveningCheck,weekdayAfternoonCheck, weekdayMorningCheck,
	weekendEveningCheck,weekendAfternoonCheck, weekendMorningCheck;
	
	public TennisDetailsFragment(){
		if(this.getArguments() == null){
			this.setArguments(new Bundle());
		}
	}
	
	private void test(){
		setTennisLevel(2.0f);
		//--
		hand = "RIGHT";
		handTextView.setText("Right");
		//--
		typeOfMatchTextView.setText(getResources().getStringArray(R.array.profile_gender)[0]);
		//--
		
		hittingAround = true;
		points = true;
		fullMatch = true;
		
		ArrayList<String> list = new ArrayList<String>();
		if(hittingAround){
			list.add(getResources().getString(R.string.label_hitting_around));
		}
		if(points){
			list.add(getResources().getString(R.string.label_points));
		}
		if(fullMatch){
			list.add(getResources().getString(R.string.label_full_match));
		}
		if(list.size() > 0){
			typeOfPlayTextView.setText(StringUtils.join(list, ", "));
		}
		
		//--
		
		singles = true;
		doubles = false;
		
		list = new ArrayList<String>();
		if(singles){
			list.add(getResources().getString(R.string.label_singles));
		}
		if(doubles){
			list.add(getResources().getString(R.string.label_doubles));
		}
		if(list.size() > 0){
			typeOfMatchTextView.setText(StringUtils.join(list, ", "));
		}
		
		//--
		
		weekdayEveningCheck = true;
		weekdayAfternoonCheck = false;
		weekdayMorningCheck = false;
		weekendEveningCheck = true;
		weekendAfternoonCheck = false;
		weekendMorningCheck = false;
        
		ArrayList<String> weekdayList = new ArrayList<String>();
		if(weekdayMorningCheck){
			weekdayList.add("Morning");
		}
		if(weekdayAfternoonCheck){
			weekdayList.add("Afternoon");
		}
		if(weekdayEveningCheck){
			weekdayList.add("Evening");
		}
		
		ArrayList<String> weekendList = new ArrayList<String>();
		if(weekendMorningCheck){
			weekendList.add("Morning");
		}
		if(weekendAfternoonCheck){
			weekendList.add("Afternoon");
		}
		if(weekendEveningCheck){
			weekendList.add("Evening");
		}
		
		StringBuilder sb = new StringBuilder();
		if(weekdayList.size() > 0){
			sb.append("Weekdays: " + StringUtils.join(weekdayList, ", "));
			if(weekendList.size() > 0){
				sb.append("\n");
			}
		}
		if(weekendList.size() > 0){
			sb.append("Weekends: " + StringUtils.join(weekendList, ", "));
		}
		if(sb.length() > 0){
			availabilityTextView.setText(sb.toString());	
		}
	
	}
	
	
	@Override
	public void onResume(Bundle arguments) {
		super.onResume(arguments);
		
//		C.logd("TennisDetailsFragment.onResume " + arguments.getFloat(C.ArgumentKey.PLAYER_LEVEL.toString()));
//		C.logd("TennisDetailsFragment.onResume2 " + getArguments().getFloat(C.ArgumentKey.PLAYER_LEVEL.toString()));
//		fragment.getArguments().putFloat(C.ArgumentKey.PLAYER_LEVEL.toString(), level);
	}

	
//	@Override
//	public void onResume() {
//		super.onResume();
//		
//		C.logd("TennisDetailsFragment.onResume2 " + getArguments().getFloat(C.ArgumentKey.PLAYER_LEVEL.toString()));
////		fragment.getArguments().putFloat(C.ArgumentKey.PLAYER_LEVEL.toString(), level);
//	}


	public void setTennisLevel(float level){
		tennisLevel = level;
		this.tennisLevelTextView.setText(String.valueOf(tennisLevel));
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_tennis_details,container, false);
		
		this.submitButton = (Button)rootView.findViewById(R.id.submit_button);
		this.handTextView = (TextView)rootView.findViewById(R.id.hand_edittext);
		this.tennisLevelTextView = (TextView)rootView.findViewById(R.id.tennis_level_textview);
		this.availabilityTextView = (TextView)rootView.findViewById(R.id.availability_textview);
		this.typeOfPlayTextView = (TextView)rootView.findViewById(R.id.type_of_play_textview);
		this.typeOfMatchTextView = (TextView)rootView.findViewById(R.id.type_of_match_textview);
		
		initHand();
		initTennisLevel();
		initTypeOfMatch();
		initAvailability();
		initTypeOfPlayTextView();
		initTypeOfMatchTextView();
		
		this.submitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				submit();
			}
		});
		
		if(Constants.env != Constants.Env.PRODUCTION){
			test();
		}
		
		initBackImageAction();
		return rootView;
	}
	
	private void initHand(){
		this.handTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showHandDialog();
			}
		});
	}
	
	private void initTennisLevel(){
		
		this.tennisLevelTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				C.logd("TennisDetailsFragment.initTennisLevel " + getTag());
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction t = fragmentManager.beginTransaction();
				t.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_in_left);
				getActivity().getIntent().putExtra(Constants.ArgumentKey.LAST_FRAGMENT_TAG.toString(), getResources().getString(R.string.tag_tennis_details));
				t.replace(R.id.content_frarment, tennisLevelFragment,getResources().getString(R.string.tag_player_level)); //
				t.addToBackStack(getResources().getString(R.string.tag_tennis_details));
				t.commit();
				((BaseFragmentActivity)getActivity()).getActivityUtils().setActionBarTitle(R.string.title_tennis_level);
			}
		});
	}
	
	private void initTypeOfPlayTextView(){
		this.typeOfPlayTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showTypeOfPlayDialog();
			}	
		});
	}
	
	private void showHandDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(TennisDetailsFragment.this.getActivity());
		final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_hand, null);
        builder.setView(view);
        final AlertDialog dialog = builder.show();
        
        view.findViewById(R.id.right_hand_layout).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				hand = "RIGHT";
				handTextView.setText("Right");
				dialog.dismiss();
			}
		});
        
        view.findViewById(R.id.left_hand_layout).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				hand = "LEFT";
				handTextView.setText("Left");
				dialog.dismiss();
			}
		});
        
	
	}
	
	private void showTypeOfPlayDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(TennisDetailsFragment.this.getActivity());
		final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_type_of_play, null);
		final CheckBox hittingAroundCheck = ((CheckBox)view.findViewById(R.id.hitting_around_checkbox));
		final CheckBox pointsCheck = ((CheckBox)view.findViewById(R.id.points_checkbox)); 
		final CheckBox fullMatchCheck = ((CheckBox)view.findViewById(R.id.full_match_checkbox));
        builder.setView(view);
        hittingAroundCheck.setChecked(hittingAround);
        pointsCheck.setChecked(points);
        fullMatchCheck.setChecked(fullMatch);
        
        final AlertDialog dialog = builder.show();
        
        view.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
        
        view.findViewById(R.id.ok_button).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				hittingAround = hittingAroundCheck.isChecked();
				points = pointsCheck.isChecked();
				fullMatch = fullMatchCheck.isChecked();
				
				ArrayList<String> list = new ArrayList<String>();
				if(hittingAround){
					list.add(getResources().getString(R.string.label_hitting_around));
				}
				if(points){
					list.add(getResources().getString(R.string.label_points));
				}
				if(fullMatch){
					list.add(getResources().getString(R.string.label_full_match));
				}
				if(list.size() > 0){
					typeOfPlayTextView.setText(StringUtils.join(list, ", "));
				}
				dialog.dismiss();
			}
		});
        
	
	}
	
	private void showAvailabilityDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(TennisDetailsFragment.this.getActivity());
		final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_availability, null);
		final CheckBox weekdayEveningCheckbox = ((CheckBox)view.findViewById(R.id.weekday_evening_checkbox));
		final CheckBox weekdayAfternoonCheckbox = ((CheckBox)view.findViewById(R.id.weekday_afternoon_checkbox));
		final CheckBox weekdayMorningCheckbox = ((CheckBox)view.findViewById(R.id.weekday_morning_checkbox));
		final CheckBox weekendEveningCheckbox = ((CheckBox)view.findViewById(R.id.weekend_evening_checkbox));
		final CheckBox weekendAfternoonCheckbox = ((CheckBox)view.findViewById(R.id.weekend_afternoon_checkbox));
		final CheckBox weekendMorningCheckbox = ((CheckBox)view.findViewById(R.id.weekend_morning_checkbox));
        builder.setView(view);
        
        weekdayEveningCheckbox.setChecked(weekdayEveningCheck);
        weekdayAfternoonCheckbox.setChecked(weekdayAfternoonCheck);
        weekdayMorningCheckbox.setChecked(weekdayMorningCheck);
        weekendEveningCheckbox.setChecked(weekendEveningCheck);
        weekendAfternoonCheckbox.setChecked(weekendAfternoonCheck);
        weekendMorningCheckbox.setChecked(weekendMorningCheck);
        
        final AlertDialog dialog = builder.show();
        
        view.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
        
        view.findViewById(R.id.ok_button).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				weekdayEveningCheck = weekdayEveningCheckbox.isChecked();
				weekdayAfternoonCheck = weekdayAfternoonCheckbox.isChecked();
				weekdayMorningCheck = weekdayMorningCheckbox.isChecked();
				weekendEveningCheck = weekendEveningCheckbox.isChecked();
				weekendAfternoonCheck = weekendAfternoonCheckbox.isChecked();
				weekendMorningCheck = weekendMorningCheckbox.isChecked();
		        
				ArrayList<String> weekdayList = new ArrayList<String>();
				if(weekdayMorningCheck){
					weekdayList.add("Morning");
				}
				if(weekdayAfternoonCheck){
					weekdayList.add("Afternoon");
				}
				if(weekdayEveningCheck){
					weekdayList.add("Evening");
				}
				
				ArrayList<String> weekendList = new ArrayList<String>();
				if(weekendMorningCheck){
					weekendList.add("Morning");
				}
				if(weekendAfternoonCheck){
					weekendList.add("Afternoon");
				}
				if(weekendEveningCheck){
					weekendList.add("Evening");
				}
				
				StringBuilder sb = new StringBuilder();
				if(weekdayList.size() > 0){
					sb.append("Weekdays: " + StringUtils.join(weekdayList, ", "));
					if(weekendList.size() > 0){
						sb.append("\n");
					}
				}
				if(weekendList.size() > 0){
					sb.append("Weekends: " + StringUtils.join(weekendList, ", "));
				}
				if(sb.length() > 0){
					availabilityTextView.setText(sb.toString());	
				}
				
				dialog.dismiss();
			}
		});
	}


	private void showTypeOfMatchDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(TennisDetailsFragment.this.getActivity());
		final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_type_of_match, null);
		final CheckBox singlesCheck = ((CheckBox)view.findViewById(R.id.singles_checkbox));
		final CheckBox doublesCheck = ((CheckBox)view.findViewById(R.id.doubles_checkbox)); 
        builder.setView(view);
        singlesCheck.setChecked(singles);
        doublesCheck.setChecked(doubles);
        
        final AlertDialog dialog = builder.show();
        
        view.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
        
        view.findViewById(R.id.ok_button).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				singles = singlesCheck.isChecked();
				doubles = doublesCheck.isChecked();
				
				ArrayList<String> list = new ArrayList<String>();
				if(singles){
					list.add(getResources().getString(R.string.label_singles));
				}
				if(doubles){
					list.add(getResources().getString(R.string.label_doubles));
				}
				if(list.size() > 0){
					typeOfMatchTextView.setText(StringUtils.join(list, ", "));
				}
				dialog.dismiss();
			}
		});
        
	
	}

	private void initTypeOfMatchTextView(){
		this.typeOfMatchTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showTypeOfMatchDialog();
			}
		});
	}
	
	private void submit(){
		UpdateTennisDetailsForm form = toForm();
		Log.d(getClass().getSimpleName(), "submit " + form);
		ServiceResponse response = Client.getInstance().updatePlayerTennisDetails(form);
		
		if(response.containsKey("exception")){
			if("com.tennissetapp.exception.NotAuthorizedException".equals(response.get("code"))){
				Intent intent = new Intent(getActivity(), SignupActivity.class);
				this.startActivity(intent);
			}
		}
		else if(response.containsKey("errors")){
			Utils.popupErrors(this.getActivity(), response);
		}
		else{
			Intent intent = new Intent(this.getActivity(), TennisMatesActivity.class);
			this.startActivity(intent);
//			Toast.makeText(getActivity(), "TEST SUCCESSFUL - PLAYER PROFILE UPDATED!", Toast.LENGTH_LONG).show();
		}
	}
	
	private void initAvailability(){
		availabilityTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showAvailabilityDialog();
			}
		});
	}
	
	private void initTypeOfMatch(){
		typeOfMatchTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(TennisDetailsFragment.this.getActivity());
	            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(TennisDetailsFragment.this.getActivity(),android.R.layout.select_dialog_singlechoice,getResources().getStringArray(R.array.profile_gender));
	            builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						typeOfMatchTextView.setText(arrayAdapter.getItem(which));
						dialog.dismiss();
					}
				});
	            
	            builder.show();
			}
		});
	}
	
	private UpdateTennisDetailsForm toForm(){
		UpdateTennisDetailsForm f = new UpdateTennisDetailsForm();
		if(this.tennisLevel != null){
			f.levelOfPlay = String.valueOf(this.tennisLevel);
		}
		//type of play
		f.doublesCheck = (this.doubles ? "on" : null);
		f.singlesCheck = (this.singles ? "on" : null);
		f.pointsCheck = (this.points ? "on" : null);
		f.fullMatchCheck = (this.fullMatch ? "on" : null);
		f.hittingAroundCheck = (this.hittingAround ? "on" : null);
		
		//availability
		f.weekdayAvailabilityAfternoonCheck = (this.weekdayAfternoonCheck ? "on" : null);
		f.weekdayAvailabilityEveningCheck = (this.weekdayEveningCheck ? "on" : null);
		f.weekdayAvailabilityMorningCheck = (this.weekdayMorningCheck ? "on" : null);
		f.weekendAvailabilityAfternoonCheck = (this.weekendAfternoonCheck ? "on" : null);
		f.weekendAvailabilityEveningCheck = (this.weekendEveningCheck ? "on" : null);
		f.weekendAvailabilityMorningCheck = (this.weekendMorningCheck ? "on" : null);
		
		//hand
		f.hand = this.hand;
		
		return f;
	}

}
