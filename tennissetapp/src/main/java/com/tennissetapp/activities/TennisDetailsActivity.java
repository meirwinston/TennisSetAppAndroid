package com.tennissetapp.activities;

import android.os.Bundle;

import com.tennissetapp.R;

public class TennisDetailsActivity extends BaseFragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.string.title_tennis_details,false,true);
		setContentView(R.layout.activity_tennis_details);
	}
}
