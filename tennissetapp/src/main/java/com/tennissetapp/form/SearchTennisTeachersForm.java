package com.tennissetapp.form;

public class SearchTennisTeachersForm extends ScrollForm{
	private static final long serialVersionUID = 1L;
	
	//location
	public String latitude;
	public String longitude;
	
	//availability
	public String availableWeekendMorning;
	public String availableWeekendAfternoon;
	public String availableWeekendEvening;
	public String availableWeekdayMorning;
	public String availableWeekdayAfternoon;
	public String availableWeekdayEvening;
	
	//distance
	public String distance = "50";
	
	public String currency = "USD";
	public String hourlyRate;
	public String teacherCertified;
	public String specialtyJuniors,specialtyAdults,specialtyTurnaments;
	@Override
	public String toString() {
		return "SearchTennisTeachersForm [latitude=" + latitude
				+ ", longitude=" + longitude + ", availableWeekendMorning="
				+ availableWeekendMorning + ", availableWeekendAfternoon="
				+ availableWeekendAfternoon + ", availableWeekendEvening="
				+ availableWeekendEvening + ", availableWeekdayMorning="
				+ availableWeekdayMorning + ", availableWeekdayAfternoon="
				+ availableWeekdayAfternoon + ", availableWeekdayEvening="
				+ availableWeekdayEvening + ", distance=" + distance
				+ ", currency=" + currency + ", hourlyRate=" + hourlyRate
				+ ", teacherCertified=" + teacherCertified
				+ ", specialtyJuniors=" + specialtyJuniors
				+ ", specialtyAdults=" + specialtyAdults
				+ ", specialtyTurnaments=" + specialtyTurnaments + "]";
	}
	
	

}
