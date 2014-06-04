package com.tennissetapp.fragments;

import com.tennissetapp.Constants;
import com.tennissetapp.R;
import com.tennissetapp.activities.BaseFragmentActivity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

public class TennisLevelFragment extends BaseFragment{
	private TextView[] levelArr;
	private Float playerLevel;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_tennis_level,container, false);
		levelArr = new TextView[]{
			(TextView)rootView.findViewById(R.id.beginner_textview),
			(TextView)rootView.findViewById(R.id.midintermediate_textview),
			(TextView)rootView.findViewById(R.id.intermediate_textview),
			(TextView)rootView.findViewById(R.id.semiadvanced_textview),
			(TextView)rootView.findViewById(R.id.advanced_textview)
		};
		
		levelArr[0].setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showBeginnerDialog();
			}	
		});
		
		levelArr[1].setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showMidIntermediateDialog();
			}	
		});
		levelArr[2].setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showIntermediateDialog();
			}	
		});
		levelArr[3].setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showSemiAdvancedDialog();
			}	
		});
		levelArr[4].setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showAdvancedDialog();
			}	
		});
		return rootView;
	}
	
	private void showBeginnerDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(TennisLevelFragment.this.getActivity());
		final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_player_level_beginner, null);
		final RadioButton[] buttons = new RadioButton[]{
				((RadioButton)view.findViewById(R.id.level_1_5_radiobutton)),
				((RadioButton)view.findViewById(R.id.level_2_radiobutton)),
				((RadioButton)view.findViewById(R.id.level_2_5_radiobutton))
		};
        builder.setView(view);
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
				if(buttons[0].isChecked()){
					backToTennisDetails(1.5f);
				}
				else if(buttons[1].isChecked()){
					backToTennisDetails(2f);
				}
				else if(buttons[2].isChecked()){
					backToTennisDetails(2.5f);
				}
				dialog.dismiss();
			}
		});
	}
	
//	private void browseBack(float newLevel){
//		FragmentManager fragmentManager = getFragmentManager();
//		FragmentTransaction t = fragmentManager.beginTransaction();
//		t.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_in_left);
//		String lastFragmentTag = getActivity().getIntent().getStringExtra(C.ArgumentKey.LAST_FRAGMENT_TAG.toString());
//		Fragment fragment = fragmentManager.findFragmentByTag(lastFragmentTag);
//		t.replace(R.id.content_frarment, fragment); //
//		t.commit();
//	}
	
	private void backToTennisDetails(float level){
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction t = fragmentManager.beginTransaction();
		String lastTag = getActivity().getIntent().getStringExtra(Constants.ArgumentKey.LAST_FRAGMENT_TAG.toString());
		Fragment fragment = fragmentManager.findFragmentByTag(lastTag);
		Log.d(getClass().getSimpleName(), "backToTennisDetails lastTag " + lastTag + " , " + fragment);
		
		if(fragment != null){
			fragment.getArguments().putFloat(Constants.ArgumentKey.PLAYER_LEVEL.toString(), level);
//			if(fragment instanceof SearchMatesFragment){
//				((SearchMatesFragment)fragment).setTennisLevel(level);
//			}
//			else
			if(fragment instanceof TennisDetailsFragment){
				((TennisDetailsFragment)fragment).setTennisLevel(level);
				((BaseFragmentActivity)getActivity()).getActivityUtils().setActionBarTitle(R.string.title_tennis_details);
			}
			t.replace(getId(), fragment);
		}
		
		t.commit();
		
		//--
//		FragmentManager fragmentManager = getFragmentManager();
//		FragmentTransaction t = fragmentManager.beginTransaction();
////		t.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
//		TennisDetailsFragment fragment = (TennisDetailsFragment)fragmentManager.findFragmentByTag(getResources().getString(R.string.tag_tennis_details));
//		fragment.setTennisLevel(level);
//		t.replace(R.id.content_frarment, fragment);
//		t.commit();
	}
	
	private void showMidIntermediateDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(TennisLevelFragment.this.getActivity());
		final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_player_level_midintermediate, null);
		final RadioButton[] buttons = new RadioButton[]{
				((RadioButton)view.findViewById(R.id.level_3_radiobutton)),
				((RadioButton)view.findViewById(R.id.level_3_5_radiobutton))
		};
        builder.setView(view);
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
				if(buttons[0].isChecked()){
					backToTennisDetails(3f);
				}
				else if(buttons[1].isChecked()){
					backToTennisDetails(3.5f);
				}
				
				dialog.dismiss();
			}
		});
	}
	
	private void showIntermediateDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(TennisLevelFragment.this.getActivity());
		final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_player_level_intermediate, null);
		final RadioButton[] buttons = new RadioButton[]{
				((RadioButton)view.findViewById(R.id.level_4_radiobutton)),
				((RadioButton)view.findViewById(R.id.level_4_5_radiobutton))
		};
        builder.setView(view);
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
				if(buttons[0].isChecked()){
					backToTennisDetails(4f);
				}
				else if(buttons[1].isChecked()){
					backToTennisDetails(4.5f);
				}
				dialog.dismiss();
			}
		});
	}
	
	private void showSemiAdvancedDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(TennisLevelFragment.this.getActivity());
		final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_player_level_semiadvanced, null);
		final RadioButton[] buttons = new RadioButton[]{
				((RadioButton)view.findViewById(R.id.level_5_radiobutton)),
				((RadioButton)view.findViewById(R.id.level_5_5_radiobutton))
		};
        builder.setView(view);
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
				if(buttons[0].isChecked()){
					backToTennisDetails(5f);
				}
				else if(buttons[1].isChecked()){
					backToTennisDetails(5.5f);
				}
				dialog.dismiss();
			}
		});
	}
	
	private void showAdvancedDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(TennisLevelFragment.this.getActivity());
		final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_player_level_advanced, null);
		final RadioButton[] buttons = new RadioButton[]{
				((RadioButton)view.findViewById(R.id.level_6_radiobutton)),
				((RadioButton)view.findViewById(R.id.level_7_radiobutton))
		};
        builder.setView(view);
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
				if(buttons[0].isChecked()){
					backToTennisDetails(6f);
				}
				else if(buttons[1].isChecked()){
					backToTennisDetails(7f);
				}
				dialog.dismiss();
			}
		});
	}
}
