package com.tennissetapp.fragments;

import java.util.ArrayList;
import org.apache.commons.lang.StringUtils;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.tennissetapp.Constants;
import com.tennissetapp.R;
import com.tennissetapp.activities.TennisMatesActivity;
import com.tennissetapp.form.ScrollForm;
import com.tennissetapp.form.SearchByNameOrEmailForm;
import com.tennissetapp.form.SearchTennisMatesForm;
import com.tennissetapp.json.GeoCodeResult;
import com.tennissetapp.json.JacksonUtils;

public class SearchMatesFragment extends Fragment {
	private TextView typeOfMatchTextView,tennisLevelTextView,typeOfPlayTextView,availabilityTextView;
	private Button submitButton;
	private boolean singles,doubles,fullMatch,points,hittingAround;
	private Float playerLevelMin, playerLevelMax;
	private boolean weekdayEveningCheck,weekdayAfternoonCheck, weekdayMorningCheck,
	weekendEveningCheck,weekendAfternoonCheck, weekendMorningCheck;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_mates_search,container, false);
		
		this.submitButton = (Button)rootView.findViewById(R.id.submit_button);
		this.tennisLevelTextView = (TextView)rootView.findViewById(R.id.tennis_level_textview);
		this.typeOfMatchTextView = (TextView)rootView.findViewById(R.id.type_of_match_textview);
		this.availabilityTextView = (TextView)rootView.findViewById(R.id.availability_textview);
		this.typeOfPlayTextView = (TextView)rootView.findViewById(R.id.type_of_play_textview);
		this.typeOfMatchTextView = (TextView)rootView.findViewById(R.id.type_of_match_textview);
		
		initTennisLevel();
		initTypeOfMatch();
		initAvailability();
		initTypeOfPlayTextView();
		initTypeOfMatchTextView();
		
		submitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				switchToResultsFragment();
			}
		});
		return rootView;
	}
	
	
	private void showTennisLevelDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(SearchMatesFragment.this.getActivity());
		final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_tennis_level, null);
        builder.setView(view);
        final AlertDialog dialog = builder.show();
       
        
        view.findViewById(R.id.level_check1_layout).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playerLevelMin = 1.5f;
				playerLevelMax = 2.9f;
				tennisLevelTextView.setText("1.5 - 2.5");
				dialog.dismiss();
			}
		});
        
        view.findViewById(R.id.level_check2_layout).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playerLevelMin = 3.0f;
				playerLevelMax = 3.9f;
				tennisLevelTextView.setText("3.0 - 3.5");
				dialog.dismiss();
			}
		});
        
        view.findViewById(R.id.level_check3_layout).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playerLevelMin = 4.0f;
				playerLevelMax = 4.9f;
				tennisLevelTextView.setText("4.0 - 4.5");
				dialog.dismiss();
			}
		});
        
        view.findViewById(R.id.level_check4_layout).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playerLevelMin = 5.0f;
				playerLevelMax = 5.9f;
				tennisLevelTextView.setText("5.0 - 5.5");
				dialog.dismiss();
			}
		});
        
        view.findViewById(R.id.level_check5_layout).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playerLevelMin = 6.0f;
				playerLevelMax = 7.0f;
				tennisLevelTextView.setText("6.0 - 7.0");
				dialog.dismiss();
			}
		});
        
	}
	
	private void initTennisLevel(){
		
		this.tennisLevelTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showTennisLevelDialog();
//				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//				builder.setItems(getResources().getStringArray(R.array.array_search_player_level), new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						switch(which){
//						case 0:
//							playerLevelMin = 1.5f;
//							playerLevelMax = 2.9f;
//							tennisLevelTextView.setText("1.5 - 2.5");
//							break;
//						case 1:
//							playerLevelMin = 3.0f;
//							playerLevelMax = 3.9f;
//							tennisLevelTextView.setText("3.0 - 3.5");
//							break;
//						case 2:
//							playerLevelMin = 4.0f;
//							playerLevelMax = 4.9f;
//							tennisLevelTextView.setText("4.0 - 4.5");
//							break;
//						case 3: 
//							playerLevelMin = 5.0f;
//							playerLevelMax = 5.9f;
//							tennisLevelTextView.setText("5.0 - 5.5");
//							break;
//						case 4:
//							
//							playerLevelMin = 6.0f;
//							playerLevelMax = 7.0f;
//							tennisLevelTextView.setText("6.0 - 7.0");
//							break;
//						}
//					}
//				});
//				builder.show();
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
	
	private void showTypeOfPlayDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(SearchMatesFragment.this.getActivity());
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
		Log.d(getClass().getSimpleName(), "showAvailabilityDialog");
		AlertDialog.Builder builder = new AlertDialog.Builder(SearchMatesFragment.this.getActivity());
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
		AlertDialog.Builder builder = new AlertDialog.Builder(SearchMatesFragment.this.getActivity());
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
	
	private void test(){
		this.tennisLevelTextView.setText("MyFirstName");
		this.typeOfMatchTextView.setText("Male");
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
				AlertDialog.Builder builder = new AlertDialog.Builder(SearchMatesFragment.this.getActivity());
	            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SearchMatesFragment.this.getActivity(),android.R.layout.select_dialog_singlechoice,getResources().getStringArray(R.array.profile_gender));
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

	
	
	
	//---
	private AutoCompleteTextView addressInput;
	private GeoCodeResult.Result geoLocation;
	
	public enum OnLoad{
		AUTO_LOAD_BY_NAME_OR_EMAIL
	}
	
	private SearchResultsMatesFragment findSearchResultsMatesFragment(){
		FragmentManager fm = getFragmentManager();
		SearchResultsMatesFragment f = (SearchResultsMatesFragment)fm.findFragmentByTag(getResources().getString(R.string.tag_mates_search_results));
		if(f == null){
			f = new SearchResultsMatesFragment();
			f.setArguments(new Bundle());
		}
		
		return f;
	}

	
	public void switchToResultsFragment(){
		SearchResultsMatesFragment resultsFragment = findSearchResultsMatesFragment();
		
		
		if(!resultsFragment.isActive()){
			resultsFragment.getArguments().putSerializable(ScrollForm.class.getName(), toSearchTennisMatesByNameForm());
			
			FragmentTransaction t = getFragmentManager().beginTransaction();
//			resultsFragment.setSearchForm((ScrollForm)resultsFragment.getArguments().get(ScrollForm.class.getName()));
			t.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			t.replace(getId(), resultsFragment,getResources().getString(R.string.tag_mates_search_results));
			t.addToBackStack(null);
			t.commit();
		}
		else{
			SearchByNameOrEmailForm f = (SearchByNameOrEmailForm)resultsFragment.getArguments().get(ScrollForm.class.getName());
			resultsFragment.loadPage(f);
		}
		
		
//		if(OnLoad.AUTO_LOAD_BY_NAME_OR_EMAIL.equals(bundle.get(OnLoad.class.getName()))){
//			resultsFragment.submitSearch();
//		}
	}
	
	@Override
	public void onResume() {
//		float l = this.getArguments().getFloat(C.ArgumentKey.PLAYER_LEVEL.toString());
//		C.logd("SearchMatesFragment ON RESUME " + l);
//		if(l != 0.0){
//			this.setTennisLevel(l);
//			this.getArguments().remove(C.ArgumentKey.PLAYER_LEVEL.toString());
//		}
		
		
		
		super.onResume();
		
		if(OnLoad.AUTO_LOAD_BY_NAME_OR_EMAIL.equals(((TennisMatesActivity)getActivity()).onLoad)){
			Log.d(getClass().getSimpleName(),"LOADING SEARCH....");
			((TennisMatesActivity)getActivity()).onLoad = null;
			switchToResultsFragment();
		}
	}
	
//	public void switchToResultsFragment(){
//		SearchByNameOrEmailForm form = toSearchTennisMatesByNameForm();
//		Bundle bundle = new Bundle();
//		bundle.putSerializable(ScrollForm.class.getName(), form);
//		switchToResultsFragment(bundle);
//	}


	private SearchByNameOrEmailForm toSearchTennisMatesByNameForm(){
		SearchByNameOrEmailForm f = new SearchByNameOrEmailForm();
//		f.nameOrEmail = this.searchEditText.getText().toString();
		return f;
	}

	private SearchTennisMatesForm toSearchTennisMatesForm(){
		SearchTennisMatesForm f = new SearchTennisMatesForm();
		if(Constants.env != Constants.Env.PRODUCTION){
			try {
				String s = "{\"address_components\" : [{\"long_name\" : \"1600\",\"short_name\" : \"1600\",\"types\" : [ \"street_number\" ]},{\"long_name\" : \"Amphitheatre Parkway\",\"short_name\" : \"Amphitheatre Pkwy\",\"types\" : [ \"route\" ]},{\"long_name\" : \"Mountain View\",\"short_name\" : \"Mountain View\",\"types\" : [ \"locality\", \"political\" ]},{\"long_name\" : \"Santa Clara\",\"short_name\" : \"Santa Clara\",\"types\" : [ \"administrative_area_level_2\", \"political\" ]},{\"long_name\" : \"California\",\"short_name\" : \"CA\",\"types\" : [ \"administrative_area_level_1\", \"political\" ]},{\"long_name\" : \"United States\",\"short_name\" : \"US\",\"types\" : [ \"country\", \"political\" ]},{\"long_name\" : \"94043\",\"short_name\" : \"94043\",\"types\" : [ \"postal_code\" ]}],\"formatted_address\" : \"1600 Amphitheatre Parkway, Mountain View, CA 94043, USA\",\"geometry\" : {\"location\" : {\"lat\" : 37.4219988,\"lng\" : -122.083954},\"location_type\" : \"ROOFTOP\",\"viewport\" : {\"northeast\" : {\"lat\" : 37.42334778029149,\"lng\" : -122.0826050197085},\"southwest\" : {\"lat\" : 37.42064981970849,\"lng\" : -122.0853029802915}}},\"types\" : [ \"street_address\" ]}";
				geoLocation = JacksonUtils.deserializeAs(s, GeoCodeResult.Result.class);
				if(geoLocation != null){
					f.latitude = String.valueOf(geoLocation.geometry.location.lat);
					f.longitude = String.valueOf(geoLocation.geometry.location.lng);
				}
			} 
			catch (Exception e) {
				Log.e(getClass().getSimpleName(),e.getMessage(),e);
			}
		}
		
		if(geoLocation == null){
			Toast.makeText(getActivity(), "Please put a complete address", Toast.LENGTH_LONG).show();
			return null;
		}
		else{
			f.latitude = String.valueOf(geoLocation.geometry.location.lat);
			f.longitude = String.valueOf(geoLocation.geometry.location.lng);
		}
		
		//level of play
		if(playerLevelMax != null && playerLevelMin != null){
			f.levelOfPlayMin = playerLevelMin.toString();
			f.levelOfPlayMax = playerLevelMax.toString();
		}
		
		//--
//		if(tennisLevel < 2){
//			f.levelOfPlayMin = "1";
//			f.levelOfPlayMax = "2";
//		}
//		else if(tennisLevel < 3){
//			f.levelOfPlayMin = "2";
//			f.levelOfPlayMax = "3";
//		}
//		else if(tennisLevel < 4){
//			f.levelOfPlayMin = "3";
//			f.levelOfPlayMax = "4";
//		}
//		else if(tennisLevel < 5){
//			f.levelOfPlayMin = "4";
//			f.levelOfPlayMax = "5";
//		}
//		else if(tennisLevel < 6){
//			f.levelOfPlayMin = "5";
//			f.levelOfPlayMax = "6";
//		}
//		else if(tennisLevel <= 7){
//			f.levelOfPlayMin = "6";
//			f.levelOfPlayMax = "7";
//		}
		
		//type of match
		if(singles){
			f.playSingles = "on";
		}
		if(doubles){
			f.playDoubles = "on";
		}
		
		//type of play
		if(points){
			f.playPoints = "on";
		}
		if(hittingAround){
			f.playHittingAround = "on";
		}
		if(fullMatch){
			f.playFullMatch = "on";
		}
		
		//availability weekdays
		if(weekdayMorningCheck){
			f.availableWeekdayMorning = "on";
		}
		if(weekdayAfternoonCheck){
			f.availableWeekdayAfternoon = "on";
		}
		if(weekdayEveningCheck){
			f.availableWeekdayEvening = "on";
		}
		
		//availability weekends
		if(weekendMorningCheck){
			f.availableWeekendMorning = "on";
		}
		if(weekendAfternoonCheck){
			f.availableWeekendAfternoon = "on";
		}
		if(weekendEveningCheck){
			f.availableWeekendEvening = "on";
		}

		return f;
	}
	
	@Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
    }
}
