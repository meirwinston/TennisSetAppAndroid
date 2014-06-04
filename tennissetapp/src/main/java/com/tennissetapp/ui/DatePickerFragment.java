package com.tennissetapp.ui;

import java.util.Calendar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {
	private int year,month,day;
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR); 
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	
	@Override
	public void onCancel(DialogInterface dialog) {
		Log.i(getClass().getSimpleName(),"OnCancel");
		super.onCancel(dialog);
	}


	@Override
	public void onStop() {
		Log.i(getClass().getSimpleName(), "OnStop");
		super.onStop();
		
	}


	public void onDateSet(DatePicker view, int year, int month, int day) {
		// Do something with the date chosen by the user
		this.year = year;
		this.month = month;
		this.day = day;
		
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	@Override
	public String toString() {
		return "DatePickerFragment [year=" + year + ", month=" + month
				+ ", day=" + day + "]";
	}
}