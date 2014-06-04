package com.tennissetapp.form;

import java.util.List;

public class CreateTennisCourtForm extends AddressForm{
	private static final long serialVersionUID = 1L;
	
	public String tennisCenterName;
	public List<String> numberOfOutdoorCourts;
	public List<String> numberOfIndoorCourts;
	public List<String> indoorSurface;
	public List<String> outdoorSurface;
	public String phoneNumber;
	public String website;
	@Override
	public String toString() {
		return "CreateTennisCourtForm [tennisCenterName=" + tennisCenterName
				+ ", numberOfOutdoorCourts=" + numberOfOutdoorCourts
				+ ", numberOfIndoorCourts=" + numberOfIndoorCourts
				+ ", indoorSurface=" + indoorSurface + ", outdoorSurface="
				+ outdoorSurface + ", phoneNumber=" + phoneNumber
				+ ", website=" + website + "] - " + super.toString();
	}
}
